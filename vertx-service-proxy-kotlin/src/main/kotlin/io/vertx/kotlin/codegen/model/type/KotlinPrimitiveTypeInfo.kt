package io.vertx.kotlin.codegen.model.type

import io.vertx.codegen.type.PrimitiveTypeInfo
import javax.lang.model.element.Element

class KotlinPrimitiveTypeInfo(typeInfo: PrimitiveTypeInfo, element : Element) : KotlinTypeInfo<PrimitiveTypeInfo>(typeInfo, element) {
    override fun format(qualified: Boolean): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}