package tk.okou.vertx.kotlin.serviceproxy

import org.jetbrains.kotlin.load.java.JvmAnnotationNames
import org.jetbrains.kotlin.metadata.ProtoBuf
import org.jetbrains.kotlin.metadata.deserialization.NameResolver
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

lateinit var metadataElement: TypeElement

fun getMetadataInfo(elementUtils: Elements, metadata: AnnotationMirror): Pair<NameResolver, ProtoBuf.Class> {
    val members = elementUtils.getAllMembers(metadataElement).filter { it is ExecutableElement }.map { it as ExecutableElement }.associate { it.simpleName.toString() to it }
    val dataElement = members[JvmAnnotationNames.METADATA_DATA_FIELD_NAME]
    val stringsElement = members[JvmAnnotationNames.METADATA_STRINGS_FIELD_NAME]
    @Suppress("UNCHECKED_CAST")
    val data = (unwrapAnnotationValue(metadata.elementValues[dataElement]) as List<String>?)?.toTypedArray()
    @Suppress("UNCHECKED_CAST")
    val strings = (unwrapAnnotationValue(metadata.elementValues[stringsElement]) as List<String>?)?.toTypedArray()
    if (data == null || strings == null) {
        throw RuntimeException("data or strings is null!")
    }
    return JvmProtoBufUtil.readClassDataFrom(data, strings)
}

private tailrec fun unwrapAnnotationValue(value: Any?): Any? =
        when (value) {
            is AnnotationValue -> unwrapAnnotationValue(value.value)
            is List<*> -> value.map(::unwrapAnnotationValue)
            else -> value
        }