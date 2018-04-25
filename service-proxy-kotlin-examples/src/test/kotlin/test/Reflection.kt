package test

import kotlin.reflect.jvm.internal.impl.serialization.ClassData
import kotlin.reflect.jvm.internal.impl.serialization.Flags
import kotlin.reflect.jvm.internal.impl.serialization.ProtoBuf
import kotlin.reflect.jvm.internal.impl.serialization.jvm.JvmProtoBufUtil

data class Foo(val foo: String)
data class Bar(val bar: String)

object FooFoo
object BarBar

fun main(args: Array<String>) {
    println("Foo is data? ${isDataClass(Foo::class.java)}")
    println("Bar is data? ${isDataClass(Bar::class.java)}")

    println("FooFoo is object? ${isObjectClass(FooFoo::class.java)}")
    println("BarBar is object? ${isObjectClass(FooFoo::class.java)}")
}

@Suppress("UNCHECKED_CAST")
fun getMetadata(target: Class<*>): ClassData? {
    val metadata = target.declaredAnnotations.singleOrNull {
        it.annotationClass.java.name == "kotlin.Metadata"
    } ?: return null

    val clazz = metadata.javaClass
    val d1 = clazz.getDeclaredMethod("d1").invoke(metadata) as? Array<String>
    val d2 = clazz.getDeclaredMethod("d2").invoke(metadata) as? Array<String>

    if (d1 == null || d2 == null) {
        return null
    }

    return JvmProtoBufUtil.readClassDataFrom(d1, d2)
}

fun isDataClass(target: Class<*>): Boolean {
    return getMetadata(target)?.let {
        Flags.IS_DATA.get(it.component2().flags)
    } ?: false
}

fun isObjectClass(target: Class<*>): Boolean {
    return getMetadata(target)?.let {
        Flags.CLASS_KIND.get(it.component2().flags) == ProtoBuf.Class.Kind.OBJECT
    } ?: false
}

fun isCompanionClass(target: Class<*>): Boolean {
    return getMetadata(target)?.let {
        Flags.CLASS_KIND.get(it.component2().flags) == ProtoBuf.Class.Kind.COMPANION_OBJECT
    } ?: false
}