package io.vertx.kotlin.codegen.model

import io.vertx.codegen.ParamInfo
import io.vertx.kotlin.codegen.model.type.KotlinTypeInfo
import javax.lang.model.element.VariableElement

/**
 * Create by yan on 2018/1/19 14:09
 */
class KotlinParamInfo(val paramInfo: ParamInfo, variableElement: VariableElement, println : (String?) -> Unit) : AbstractKotlinInfo(variableElement.annotationMirrors) {
    val name : String
        get() = paramInfo.name

    val type : KotlinTypeInfo<*> = KotlinTypeInfo.create(paramInfo.type, variableElement, println)

}