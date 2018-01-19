package io.vertx.serviceproxy

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.eventbus.ReplyFailure
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth

import java.util.HashSet
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Function

/**
 * Create an event bus service interceptor using a JWT auth that will verify all requests before the service is invoked
 *
 *
 * Once a JWT is validated it will be queried for authorities. If authorities are missing a error 403 is returned.
 */
class ServiceJWTInterceptor : Function<Message<JsonObject>, Future<Message<JsonObject>>> {

    private var jwtAuth: JWTAuth? = null
    private var authorities: MutableSet<String>? = null

    /**
     * Set a JWT auth that will verify all requests before the service is invoked.
     *
     * @param jwtAuth a JWT auth
     * @return self
     */
    fun setJwtAuth(jwtAuth: JWTAuth): ServiceJWTInterceptor {
        this.jwtAuth = jwtAuth
        return this
    }

    /**
     * Set the required authorities for the service, once a JWT is validated it will be
     * queried for these authorities. If authorities are missing a error 403 is returned.
     *
     * @param authorities set of authorities
     * @return self
     */
    fun setAuthorities(authorities: MutableSet<String>): ServiceJWTInterceptor {
        this.authorities = authorities
        return this
    }

    /**
     * Add a single authority to the authorities set.
     *
     * @param authority authority
     * @return self
     */
    fun addAuthority(authority: String): ServiceJWTInterceptor {
        if (authorities == null) {
            authorities = HashSet()
        }
        authorities!!.add(authority)
        return this
    }

    /**
     * Clear the current set of authorities.
     * @return self
     */
    fun clearAuthorities(): ServiceJWTInterceptor {
        if (authorities != null) {
            authorities!!.clear()
        }
        return this
    }

    override fun apply(msg: Message<JsonObject>): Future<Message<JsonObject>> {
        val authorization = msg.headers().get("auth-token") ?: return Future.failedFuture<Message<JsonObject>>(ReplyException(ReplyFailure.RECIPIENT_FAILURE, 401, "Unauthorized"))

        val fut = Future.future<Message<JsonObject>>()

        jwtAuth!!.authenticate(JsonObject().put("jwt", authorization), { authenticate ->
            if (authenticate.failed()) {
                fut.fail(ReplyException(ReplyFailure.RECIPIENT_FAILURE, 500, authenticate.cause().message))
                return@authenticate
            }

            val user = authenticate.result()

            if (user == null) {
                fut.fail(ReplyException(ReplyFailure.RECIPIENT_FAILURE, 403, "Forbidden"))
                return@authenticate
            }

            val requiredCount = if (authorities == null) 0 else authorities!!.size

            if (requiredCount > 0) {

                val count = AtomicInteger()
                val sentFailure = AtomicBoolean()

                val authHandler : (AsyncResult<Boolean>) -> Unit = { res ->
                    if (res.succeeded()) {
                        if (res.result()) {
                            if (count.incrementAndGet() == requiredCount) {
                                // Has all required authorities
                                fut.complete(msg)
                            }
                        } else {
                            if (sentFailure.compareAndSet(false, true)) {
                                fut.fail(ReplyException(ReplyFailure.RECIPIENT_FAILURE, 403, "Forbidden"))
                            }
                        }
                    } else {
                        fut.fail(ReplyException(ReplyFailure.RECIPIENT_FAILURE, 500, res.cause().message))
                    }
                }
                for (authority in authorities!!) {
                    if (!sentFailure.get()) {
                        user.isAuthorised(authority, authHandler)
                    }
                }
            } else {
                // No auth required
                fut.complete(msg)
            }
        })

        return fut
    }
}
