package tk.okou.vertx.kotlin.serviceproxy.model

import ext.kotlin.kotlinClassHeader
import io.vertx.codegen.GenException
import io.vertx.codegen.annotations.VertxGen
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import io.vertx.serviceproxy.model.ProxyModel as VertxProxyModel
import javax.lang.model.element.ElementKind.*

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

    private fun traverseType(elem : Element) {
        when(elem.kind) {
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
                concrete = elem.getAnnotation(VertxGen::class.java) == null || elem.getAnnotation(VertxGen::class.java).concrete
                val annotation = elem.annotationMirrors.filter {
                    (it.annotationType.asElement() as TypeElement).qualifiedName.toString() == "kotlin.Metadata"
                }.first()

                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, annotation.annotationType.toString())
//                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, elem.toString())
                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, annotation.elementValues.size.toString())
//                env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, elem.kotlinClassHeader?.data.toString())
//                annotation.elementValues.forEach {key, value ->
//                    env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, key.simpleName)
//                    env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, value.javaClass.toString())
//                }
            }
            else -> {
                throw GenException(elem, "@VertxGen can only be used with interfaces or enums in " + elem.asType().toString())
            }
        }

    }

    private fun determineApiTypes(){

    }




    fun println(any : Any){
        env.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, any.toString())
    }

}