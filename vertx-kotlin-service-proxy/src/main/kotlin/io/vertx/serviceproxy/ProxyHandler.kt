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
import io.vertx.core.Handler
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.json.JsonObject
import java.util.function.Function

/**
 * @author [Tim Fox](http://tfox.org)
 */
abstract class ProxyHandler : Handler<Message<JsonObject>> {

    @Suppress("MemberVisibilityCanPrivate")
    protected var closed: Boolean = false
    @Suppress("MemberVisibilityCanPrivate")
    protected lateinit var consumer: MessageConsumer<JsonObject>

    open fun close() {
        consumer.unregister()
        closed = true
    }

    /**
     * Register the proxy handle on the event bus.
     *
     * @param eventBus the event bus
     * @param address the proxy address
     */
    @JvmOverloads
    fun register(eventBus: EventBus, address: String, interceptors: List<Function<Message<JsonObject>, Future<Message<JsonObject>>>>? = null): MessageConsumer<JsonObject> {
        var handler : (Message<JsonObject>) -> Unit = this::handle
        if (interceptors != null) {
            for (interceptor in interceptors) {
                val prev = handler
                handler = { msg ->
                    val fut = interceptor.apply(msg)
                    fut.setHandler { ar ->
                        if (ar.succeeded()) {
                            prev(msg)
                        } else {
                            val exception = ar.cause() as ReplyException
                            msg.fail(exception.failureCode(), exception.message)
                        }
                    }
                }
            }
        }
        consumer = eventBus.consumer(address, handler)
        return consumer
    }
}