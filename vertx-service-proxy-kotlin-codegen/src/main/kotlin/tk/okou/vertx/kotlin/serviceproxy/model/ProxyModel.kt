package tk.okou.vertx.kotlin.serviceproxy.model

import io.vertx.codegen.GenException
import io.vertx.codegen.TypeParamInfo
import io.vertx.codegen.annotations.VertxGen
import org.jetbrains.kotlin.load.java.JvmAnnotationNames
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil
import org.jetbrains.kotlin.serialization.deserialization.getName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import io.vertx.serviceproxy.model.ProxyModel as VertxProxyModel
import javax.lang.model.element.ElementKind.*
import javax.lang.model.element.ExecutableElement
import org.jetbrains.kotlin.metadata.ProtoBuf.Type
import tk.okou.vertx.kotlin.serviceproxy.getMetadataInfo

class ProxyModel(env: ProcessingEnvironment, modelElt: TypeElement) : VertxProxyModel(env, modelElt) {

    init {
    }

    override fun getKind(): String {
        return "kotlinProxy"
    }

    override fun process(): Boolean {
        return if (!processed) {
            traverseType(this.modelElt)
            determineApiTypes()
            processTypeAnnotations()
            processed = true
            true
        } else {
            false
        }

    }

    private fun processTypeAnnotations() {

    }

    private fun traverseType(elem: Element) {
        when (elem.kind) {
            ENUM, INTERFACE -> {
                if (ifaceFQCN != null) {
                    throw GenException(elem, "Can only have one interface per file")
                }
                type = typeFactory.create(elem.asType()).raw
                if (this.module == null) {
                    throw GenException(this.element, "Declaration annotated with @VertxGen must be under a package annotated" +
                            "with @ModuleGen. Check that the package '" + this.fqn +
                            "' or a parent package contains a 'package-info.java' using the @ModuleGen annotation")
                }
                ifaceFQCN = elem.asType().toString()
                ifaceSimpleName = elem.simpleName.toString()
                ifacePackageName = elementUtils.getPackageOf(elem).qualifiedName.toString()
                ifaceComment = elementUtils.getDocComment(elem)
                doc = docFactory.createDoc(elem)
                val vertxGen = elem.getAnnotation(VertxGen::class.java)
                concrete = vertxGen == null || vertxGen.concrete

                val metaDataElement = elementUtils.getTypeElement("kotlin.Metadata")
                val metadata = elem.annotationMirrors.first {
                    (it.annotationType.asElement() as TypeElement) == metaDataElement
                }
                val (nameResolver, classData) = getMetadataInfo(elementUtils, metadata)
                val varTypeArgs = classData.typeParameterList
                val typeParams = ArrayList<TypeParamInfo>()
                for (typeArg in varTypeArgs) {
                    if (typeArg.upperBoundCount != 0) {
                        throw GenException(elem, "Type variable bounds not supported " + typeArg.upperBoundList.map {
                            nameResolver.getName(it.name())
                        })
                    }
                    System.err.println(nameResolver.getName(typeArg.name))
                }
                classData.functionList.forEach {
                    it.valueParameterList.forEach {
                        it.type.flags
//                        it.n
                        System.err.println(nameResolver.getName(it.type.className).toString() + "," + nameResolver.getName(it.name))
                    }
                    it.typeParameterList.forEach{
                        System.err.println(nameResolver.getName(it.name))
                    }
                }
//                var st = typeUtils.directSupertypes(tm)

//                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "${an?.elementValues?.size}")
//                an?.elementValues?.forEach { key, value ->
//                    env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "$key === $value")
//                }
//
//                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "${an == null}")

            }
            else -> {
                throw GenException(elem, "@VertxGen can only be used with interfaces or enums in " + elem.asType().toString())
            }
        }

    }

    private fun determineApiTypes() {

    }




    private fun Type.name() =
            when {
                this.hasTypeParameterName() -> this.typeParameterName
                this.hasClassName() -> this.className
                this.hasTypeAliasName() -> this.typeAliasName
                else -> throw RuntimeException("err : ${this}")
            }



}