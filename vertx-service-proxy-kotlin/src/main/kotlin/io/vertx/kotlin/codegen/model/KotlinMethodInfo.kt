package io.vertx.kotlin.codegen.model

import io.vertx.codegen.MethodKind
import io.vertx.codegen.ProxyMethodInfo
import io.vertx.kotlin.codegen.model.type.KotlinTypeInfo
import javax.annotation.processing.Messager
import javax.lang.model.element.ExecutableElement
import javax.tools.Diagnostic

/**
 * Create by yan on 2018/1/19 14:02
 */
class KotlinMethodInfo(val methodInfo: ProxyMethodInfo, val methodElement : ExecutableElement, println : (String?) -> Unit) {
    val name : String get() = methodInfo.name
    val kind : MethodKind get() = methodInfo.kind
    val returnType = KotlinReturnTypeInfo(methodInfo.returnType, methodElement.returnType)
//    val returnType = KotlinTypeInfo.create(methodInfo.returnType, methodElement.returnType)
    val params = ArrayList<KotlinParamInfo>(methodInfo.params.size)
    val fluent : Boolean get() = methodInfo.isFluent
    val proxyClose : Boolean get() = methodInfo.isProxyClose
    init {
        val rawParams = methodInfo.params
        val rawElements = methodElement.parameters
        rawParams.mapIndexedTo(params) { index, paramInfo ->
            KotlinParamInfo(paramInfo, rawElements[index], println)
        }

//
//        methodElement.parameters
//        println("============Test")
//        methodElement.returnType.annotationMirrors
//        println("methodName : ${methodInfo.name}")
//        println("return annotation:${methodElement.returnType.annotationMirrors.size}")
//        println("method annotation:${methodElement.annotationMirrors.size}")
//        methodElement.annotationMirrors.forEach {
//            println("method annotation ${it.annotationType}")
//        }
//        methodElement.parameters.forEach {param ->
//            println("param annotation:${param.annotationMirrors.size}")
//        }
    }
}