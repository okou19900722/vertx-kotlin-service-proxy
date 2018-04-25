package io.vertx.kotlin.codegen.model.type

import io.vertx.codegen.type.ParameterizedTypeInfo
import javax.lang.model.element.VariableElement

/**
 * Create by yan on 2018/1/20 11:59
 */
class KotlinParameterizedTypeInfo(typeInfo : ParameterizedTypeInfo, element : VariableElement, println : (String?) -> Unit) : KotlinTypeInfo<ParameterizedTypeInfo>(typeInfo, element){
    val raw : KotlinClassTypeInfo = KotlinClassTypeInfo(typeInfo.raw, element)

    val args = ArrayList<KotlinTypeInfo<*>>()
    init {
        println("args.size================${typeInfo.args.size}")
        println("args.size================${element.enclosedElements}")
    }


    override fun format(qualified: Boolean): String {
        val buf = StringBuffer(raw.format(qualified)).append('<')

        return buf.toString()
    }
}