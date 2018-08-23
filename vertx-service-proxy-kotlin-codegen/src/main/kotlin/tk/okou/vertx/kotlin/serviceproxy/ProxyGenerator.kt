package tk.okou.vertx.kotlin.serviceproxy

import io.vertx.codegen.Generator
import tk.okou.vertx.kotlin.serviceproxy.model.ProxyModel
import java.io.PrintWriter
import java.io.StringWriter

class ProxyGenerator : Generator<ProxyModel>() {
    init {
        this.name = "kotlinServiceProxy"
        this.kinds = setOf("kotlinProxy")
    }

    override fun relativeFilename(model: ProxyModel?): String {
        if (model == null) {
            throw RuntimeException("xxxxx")
        }
        return "kotlin/${model.ifaceFQCN.replace(".", "/")}VertxEBProxy"
    }

    override fun render(model: ProxyModel?, index: Int, size: Int, session: MutableMap<String, Any>?): String {
        val buffer = StringWriter()
        val writer = PrintWriter(buffer)


        return buffer.toString()
    }
}