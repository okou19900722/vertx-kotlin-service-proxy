package io.vertx.serviceproxy

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.eventbus.ReplyFailure
import io.vertx.core.json.JsonObject

/**
 * An Exception to be returned from Service implementations.
 *
 * @author [Dan O'Reilly](mailto:oreilldf@gmail.com)
 */
class ServiceException @JvmOverloads constructor(failureCode: Int, message: String,
                                                 /**
                                                  * Get the Debugging information provided to this ServiceException
                                                  *
                                                  * @return The debug info.
                                                  */
                                                 val debugInfo: JsonObject = JsonObject()) : ReplyException(ReplyFailure.RECIPIENT_FAILURE, failureCode, message) {
    companion object {

        /**
         * Create a failed Future containing a ServiceException.
         *
         * @param failureCode The failure code.
         * @param message The failure message.
         * @param <T> The type of the AsyncResult.
         * @return A failed Future containing the ServiceException.
        </T> */
        fun <T> fail(failureCode: Int, message: String): AsyncResult<T> {
            return Future.failedFuture(ServiceException(failureCode, message))
        }

        /**
         *
         * Create a failed Future containing a ServiceException.
         *
         * @param failureCode The failure code.
         * @param message The failure message.
         * @param debugInfo The debug info.
         * @param <T> The type of the AsyncResult.
         * @return A failed Future containing the ServiceException.
        </T> */
        fun <T> fail(failureCode: Int, message: String, debugInfo: JsonObject): AsyncResult<T> {
            return Future.failedFuture(ServiceException(failureCode, message, debugInfo))
        }
    }

}

