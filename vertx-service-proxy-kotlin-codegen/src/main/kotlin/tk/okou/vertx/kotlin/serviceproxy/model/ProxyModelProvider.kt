package tk.okou.vertx.kotlin.serviceproxy.model

import io.vertx.codegen.ModelProvider
import tk.okou.vertx.kotlin.serviceproxy.ProxyGen
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

class ProxyModelProvider : ModelProvider {
    override fun getModel(env: ProcessingEnvironment, elt: TypeElement): ProxyModel? {
        return elt.getAnnotation(ProxyGen::class.java)?.let { ProxyModel(env, elt) }
    }
}