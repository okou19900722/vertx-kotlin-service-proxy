package tk.okou.vertx.kotlin.serviceproxy

import io.vertx.codegen.TypeParamInfo
import io.vertx.codegen.type.TypeInfo
import io.vertx.codegen.type.TypeUse
import io.vertx.codegen.type.TypeVariableInfo
import org.jetbrains.kotlin.serialization.deserialization.getName
import java.util.ArrayList
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import io.vertx.codegen.type.TypeMirrorFactory as JavaTypeMirrorFactory

class TypeMirrorFactory(val elementUtils: Elements, typeUtils: Types) : JavaTypeMirrorFactory(elementUtils, typeUtils) {
    override fun create(type: TypeMirror): TypeInfo {
        return create(null, type)
    }
    override fun create(use: TypeUse?, type: TypeMirror): TypeInfo {
        return super.create(use, type)
    }
    override fun create(type: DeclaredType): TypeInfo {
        return super.create(type)
    }
    override fun create(use: TypeUse?, type: DeclaredType): TypeInfo {
        return super.create(use, type)
    }


    override fun create(use: TypeUse?, type: TypeVariable): TypeVariableInfo {
        return super.create(use, type)
    }
    fun createTypeParams(type: DeclaredType) : List<TypeParamInfo.Class>{
        val metadata = type.annotationMirrors.firstOrNull {
            (it.annotationType.asElement() as TypeElement) == metadataElement
        }
        if (metadata == null) {
            return createJavaTypeParams(type)
        } else {
            val (nameResolver, classData) = getMetadataInfo(elementUtils, metadata)
            val list = ArrayList<TypeParamInfo.Class>(classData.typeParameterCount)
            classData.typeParameterList.asSequence().mapIndexed { index , it->
                val name = nameResolver.getName(it.name).toString()
                TypeParamInfo.Class(name, index, name)
            }
            return listOf()
        }
    }

    private fun createJavaTypeParams(type: DeclaredType): List<TypeParamInfo.Class> {
        val typeParams = ArrayList<TypeParamInfo.Class>()
        val elt = type.asElement() as TypeElement
        val typeParamElts = elt.typeParameters
        for (index in typeParamElts.indices) {
            val typeParamElt = typeParamElts[index]
            typeParams.add(TypeParamInfo.Class(elt.qualifiedName.toString(), index, typeParamElt.simpleName.toString()))
        }
        return typeParams
    }
}