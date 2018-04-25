package test

import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor

/**
 * Create by yan on 2018/1/22 11:18
 */
class Test {
}

fun main(args: Array<String>) {
    val kca = Test::class
    kca.declaredFunctions.forEach {
        it.valueParameters.forEach {
            it.isOptional
        }
    }
}