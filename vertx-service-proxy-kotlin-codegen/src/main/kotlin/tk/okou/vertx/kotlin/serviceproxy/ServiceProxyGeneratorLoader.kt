package tk.okou.vertx.kotlin.serviceproxy

import io.vertx.codegen.Generator
import io.vertx.codegen.GeneratorLoader
import java.util.stream.Stream
import javax.annotation.processing.ProcessingEnvironment

class ServiceProxyGeneratorLoader : GeneratorLoader {
    override fun loadGenerators(processingEnv: ProcessingEnvironment?): Stream<Generator<*>> {
        return Stream.of(ServiceProxyGenerator())
    }

}