package test

import tk.okou.vertx.kotlin.serviceproxy.ProxyGen


@ProxyGen
/**
 * xxxx
 */
interface TestService<A> {
    fun <A> test(value : String, b : A)
}