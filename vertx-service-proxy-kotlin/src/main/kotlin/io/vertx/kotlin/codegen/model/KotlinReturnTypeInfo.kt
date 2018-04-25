package io.vertx.kotlin.codegen.model

import io.vertx.codegen.type.TypeInfo
import javax.lang.model.type.TypeMirror

/**
 * Create by yan on 2018/1/19 19:45
 */
class KotlinReturnTypeInfo(val raw : TypeInfo, val element : TypeMirror) : AbstractKotlinInfo(element.annotationMirrors) {
    init {
//        element.
    }
}