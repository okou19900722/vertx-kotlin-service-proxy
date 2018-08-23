package tk.okou.vertx.kotlin.serviceproxy.model

import io.vertx.codegen.ModelProvider
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

class ProxyModelProvider : ModelProvider {
    override fun getModel(env: ProcessingEnvironment, elt: TypeElement): ProxyModel? {
        val metadata = elt.annotationMirrors.filter {
            (it.annotationType.asElement() as TypeElement).qualifiedName.toString() == "kotlin.Metadata"
        }.firstOrNull()
        return metadata?.let { ProxyModel(env, elt) }
    }

}