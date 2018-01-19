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

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject

import java.util.Objects

/**
 * @author [Tim Fox](http://tfox.org)
 *
 */
@Deprecated("for a more robust proxy creation see: {@link ServiceBinder}")
object ProxyHelper {

    val DEFAULT_CONNECTION_TIMEOUT = (5 * 60).toLong() // 5 minutes

    fun <T> createProxy(clazz: Class<T>, vertx: Vertx, address: String): T {
        return ServiceProxyBuilder(vertx)
                .setAddress(address)
                .build(clazz)
    }

    fun <T> createProxy(clazz: Class<T>, vertx: Vertx, address: String, options: DeliveryOptions): T {
        return ServiceProxyBuilder(vertx)
                .setAddress(address)
                .setOptions(options)
                .build(clazz)
    }

    /**
     * Registers a service on the event bus.
     *
     * @param clazz   the service class (interface)
     * @param vertx   the vert.x instance
     * @param service the service object
     * @param address the address on which the service is published
     * @param <T>     the type of the service interface
     * @return the consumer used to unregister the service
    </T> */
    fun <T : Any> registerService(clazz: Class<T>, vertx: Vertx, service: T, address: String): MessageConsumer<JsonObject> {
        return ServiceBinder(vertx)
                .setAddress(address)
                .register(clazz, service)
    }

    fun <T : Any> registerService(clazz: Class<T>, vertx: Vertx, service: T, address: String,
                            timeoutSeconds: Long): MessageConsumer<JsonObject> {
        return ServiceBinder(vertx)
                .setAddress(address)
                .setTimeoutSeconds(timeoutSeconds)
                .register(clazz, service)
    }

    fun <T : Any> registerService(clazz: Class<T>, vertx: Vertx, service: T, address: String,
                            topLevel: Boolean,
                            timeoutSeconds: Long): MessageConsumer<JsonObject> {
        return ServiceBinder(vertx)
                .setAddress(address)
                .setTopLevel(topLevel)
                .setTimeoutSeconds(timeoutSeconds)
                .register(clazz, service)
    }

    /**
     * Unregisters a published service.
     *
     * @param consumer the consumer returned by [.registerService].
     */
    fun unregisterService(consumer: MessageConsumer<JsonObject>) {
        Objects.requireNonNull<MessageConsumer<JsonObject>>(consumer)
        if (consumer is ProxyHandler) {
            (consumer as ProxyHandler).close()
        } else {
            // Fall back to plain unregister.
            consumer.unregister()
        }
    }
}
