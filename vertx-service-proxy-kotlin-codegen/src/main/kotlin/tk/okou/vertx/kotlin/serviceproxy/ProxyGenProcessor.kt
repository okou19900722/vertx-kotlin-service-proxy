package tk.okou.vertx.kotlin.serviceproxy

import io.vertx.codegen.Generator
import io.vertx.codegen.Model
import java.util.function.Predicate
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.tools.Diagnostic
import io.vertx.serviceproxy.ServiceProxyProcessor as VertxServiceProxyProcessor

@SupportedOptions("codegen.output")
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
class ProxyGenProcessor : VertxServiceProxyProcessor() {

    override fun filterGenerators(): Predicate<Generator<Model>> {
        return Predicate{
            it.name.contains("kotlinServiceProxy") || it.name == "data_object_converters"
        }
    }
}