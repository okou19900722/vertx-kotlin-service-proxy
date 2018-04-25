package io.vertx.kotlin.codegen.model.type

import io.vertx.codegen.type.ClassTypeInfo
import javax.lang.model.element.Element

/**
 * Create by yan on 2018/1/20 12:34
 */
class KotlinClassTypeInfo(typeInfo : ClassTypeInfo, element : Element) : KotlinTypeInfo<ClassTypeInfo>(typeInfo, element) {

    override fun format(qualified: Boolean): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}