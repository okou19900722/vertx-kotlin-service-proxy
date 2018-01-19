package io.vertx.kotlin.codegen.model

import io.vertx.codegen.MethodInfo
import javax.annotation.processing.Messager
import javax.lang.model.element.ExecutableElement
import javax.tools.Diagnostic

/**
 * Create by yan on 2018/1/19 14:02
 */
class KotlinMethodInfo(val raw : MethodInfo, val methodElement : ExecutableElement, val messager : Messager) {
    init {
        methodElement.returnType.annotationMirrors
        println("methodName : ${raw.name}")
        println("return annotation:${methodElement.returnType.annotationMirrors.size}")
        println("method annotation:${methodElement.annotationMirrors.size}")
        methodElement.parameters.forEach {param ->
            println("param annotation:${param.annotationMirrors.size}")
        }


    }

    private fun println(msg : String?){
        messager.printMessage(Diagnostic.Kind.WARNING, msg)
    }
}