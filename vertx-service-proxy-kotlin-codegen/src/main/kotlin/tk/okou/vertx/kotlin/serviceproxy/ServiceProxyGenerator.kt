package tk.okou.vertx.kotlin.serviceproxy

import io.vertx.codegen.Generator
import tk.okou.vertx.kotlin.serviceproxy.model.ProxyModel

class ServiceProxyGenerator : Generator<ProxyModel>() {
    init {
        this.name = "kotlinServiceProxy"
    }
}