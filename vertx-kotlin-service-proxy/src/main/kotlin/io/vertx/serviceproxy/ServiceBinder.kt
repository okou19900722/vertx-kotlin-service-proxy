/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.serviceproxy

import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject

import java.lang.reflect.Constructor
import java.util.*
import java.util.function.Function

/**
 * A binder for Service Proxies which state can be reused during the binder lifecycle.
 *
 * @author [Paulo Lopes](mailto:plopes@redhat.com)
 */
class ServiceBinder
/**
 * Creates a factory.
 *
 * @param vertx a non null instance of vertx.
 */
(private val vertx: Vertx) {

    private var address: String? = null
    private var topLevel = true
    private var timeoutSeconds = DEFAULT_CONNECTION_TIMEOUT
    private var interceptors: MutableList<Function<Message<JsonObject>, Future<Message<JsonObject>>>>? = null

    init {
        Objects.requireNonNull<Vertx>(vertx)
    }

    /**
     * Set the address to use on the subsequent proxy creations or service registrations.
     *
     * @param address an eventbus address
     * @return self
     */
    fun setAddress(address: String): ServiceBinder {
        this.address = address
        return this
    }

    /**
     * Set if the services to create are a top level services.
     *
     * @param topLevel true for top level (default: true)
     * @return self
     */
    fun setTopLevel(topLevel: Boolean): ServiceBinder {
        this.topLevel = topLevel
        return this
    }

    /**
     * Set the default timeout in seconds while waiting for a reply.
     *
     * @param timeoutSeconds the default timeout (default: 5 minutes)
     * @return self
     */
    fun setTimeoutSeconds(timeoutSeconds: Long): ServiceBinder {
        this.timeoutSeconds = timeoutSeconds
        return this
    }

    fun addInterceptor(interceptor: Function<Message<JsonObject>, Future<Message<JsonObject>>>): ServiceBinder {
        if (interceptors == null) {
            interceptors = ArrayList()
        }
        interceptors!!.add(interceptor)
        return this
    }

    /**
     * Registers a service on the event bus.
     *
     * @param clazz   the service class (interface)
     * @param service the service object
     * @param <T>     the type of the service interface
     * @return the consumer used to unregister the service
    </T> */
    fun <T : Any> register(clazz: Class<T>, service: T): MessageConsumer<JsonObject> {
        Objects.requireNonNull<String>(address)

        val handlerClassName = clazz.name + "VertxKtProxyHandler"
        val handlerClass = loadClass(handlerClassName, clazz)
        val constructor = getConstructor(handlerClass, Vertx::class.java, clazz, Boolean::class.java, Long::class.java)
        val instance = createInstance(constructor, vertx, service, topLevel, timeoutSeconds)
        val handler = instance as ProxyHandler
        // register
        return handler.register(vertx.eventBus(), address!!, interceptors)
    }

    /**
     * Unregisters a published service.
     *
     * @param consumer the consumer returned by [.register].
     */
    fun unregister(consumer: MessageConsumer<JsonObject>) {
        Objects.requireNonNull<MessageConsumer<JsonObject>>(consumer)

        if (consumer is ProxyHandler) {
            (consumer as ProxyHandler).close()
        } else {
            // Fall back to plain unregister.
            consumer.unregister()
        }
    }

    companion object {

        val DEFAULT_CONNECTION_TIMEOUT = (5 * 60).toLong() // 5 minutes

        private fun loadClass(name: String, origin: Class<*>): Class<*> {
            try {
                return origin.classLoader.loadClass(name)
            } catch (e: ClassNotFoundException) {
                throw IllegalStateException("Cannot find proxyClass: " + name, e)
            }

        }

        private fun getConstructor(clazz: Class<*>, vararg types: Class<*>): Constructor<*> {
            try {
                return clazz.getDeclaredConstructor(*types)
            } catch (e: NoSuchMethodException) {
                throw IllegalStateException("Cannot find constructor on: " + clazz.name, e)
            }

        }

        private fun createInstance(constructor: Constructor<*>, vararg args: Any): Any {
            try {
                return constructor.newInstance(*args)
            } catch (e: Exception) {
                throw IllegalStateException("Failed to call constructor on", e)
            }

        }
    }
}
