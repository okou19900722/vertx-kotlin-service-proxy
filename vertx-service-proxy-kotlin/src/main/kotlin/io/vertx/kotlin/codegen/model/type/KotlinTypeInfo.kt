package io.vertx.kotlin.codegen.model.type

import io.vertx.codegen.type.ClassTypeInfo
import io.vertx.codegen.type.ParameterizedTypeInfo
import io.vertx.codegen.type.PrimitiveTypeInfo
import io.vertx.codegen.type.TypeInfo
import javax.lang.model.element.Element
import javax.lang.model.element.VariableElement

/**
 * Create by yan on 2018/1/20 11:58
 */
abstract class KotlinTypeInfo<out T : TypeInfo>(val typeInfo: T, val element : Element) {
    companion object {
        @JvmStatic
        fun create(typeInfo : TypeInfo, element : Element, println : (String?) -> Unit) : KotlinTypeInfo<*>{
            return when(typeInfo) {
                is ParameterizedTypeInfo -> {
                    KotlinParameterizedTypeInfo(typeInfo, element as VariableElement, println)
                }
//            is ApiTypeInfo -> {
//
//            }
            is ClassTypeInfo -> {
                KotlinClassTypeInfo(typeInfo, element)
            }
//            is DataObjectTypeInfo -> {
//
//            }
//            is EnumTypeInfo -> {
//
//            }
                is PrimitiveTypeInfo -> {
                    KotlinPrimitiveTypeInfo(typeInfo, element)
                }
//            is TypeVariableInfo -> {
//
//            }
//            is VoidTypeInfo -> {
//
//            }
                else -> {
                    throw RuntimeException("[${typeInfo.javaClass}]这个typeInfo未实现，确定哪里需要！")
                }
            }
        }
    }

    abstract fun format(qualified : Boolean) : String
}