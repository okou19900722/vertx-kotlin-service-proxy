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

import java.lang.reflect.Constructor
import java.util.Objects

/**
 * A builder for Service Proxies which state can be reused during the builder lifecycle.
 *
 * @author [Paulo Lopes](mailto:plopes@redhat.com)
 */
class ServiceProxyBuilder
/**
 * Creates a builder.
 *
 * @param vertx a non null instance of vertx.
 */
(private val vertx: Vertx) {

    private lateinit var address: String
    private var options: DeliveryOptions? = null
    private var token: String? = null

    init {
        Objects.requireNonNull<Vertx>(vertx)
    }

    /**
     * Set the address to use on the subsequent proxy creations or service registrations.
     *
     * @param address an eventbus address
     * @return self
     */
    fun setAddress(address: String): ServiceProxyBuilder {
        this.address = address
        return this
    }

    /**
     * Set a JWT token to be used on proxy calls.
     *
     * @param token a JWT token
     * @return self
     */
    fun setToken(token: String): ServiceProxyBuilder {
        this.token = token
        return this
    }

    /**
     * Set delivery options to be used during a proxy call.
     *
     * @param options delivery options
     * @return self
     */
    fun setOptions(options: DeliveryOptions): ServiceProxyBuilder {
        this.options = options
        return this
    }

    @Suppress("UNCHECKED_CAST")
            /**
     * Creates a proxy to a service on the event bus.
     *
     * @param clazz   the service class (interface)
     * @param <T>     the type of the service interface
     * @return a proxy to the service
    </T> */
    fun <T> build(clazz: Class<T>): T {
        Objects.requireNonNull<String>(address)

        val proxyClassName = clazz.name + "VertxKtEBProxy"
        val proxyClass = loadClass(proxyClassName, clazz)
        val constructor: Constructor<*>
        val instance: Any

        if (token != null) {
            if (options == null) {
                options = DeliveryOptions()
            }
            options!!.addHeader("auth-token", token!!)
        }

        if (options == null) {
            constructor = getConstructor(proxyClass, Vertx::class.java, String::class.java)
            instance = createInstance(constructor, vertx, address)
        } else {
            constructor = getConstructor(proxyClass, Vertx::class.java, String::class.java, DeliveryOptions::class.java)
            instance = createInstance(constructor, vertx, address, options!!)
        }
        return instance as T
    }

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
