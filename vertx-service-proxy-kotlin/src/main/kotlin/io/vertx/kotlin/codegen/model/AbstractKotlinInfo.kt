package io.vertx.kotlin.codegen.model

import javax.lang.model.element.AnnotationMirror

/**
 * Create by yan on 2018/1/19 19:56
 */
open class AbstractKotlinInfo(val annotationMirrors : List<AnnotationMirror>) {
    val nullable : Boolean = annotationMirrors.any { it.annotationType.toString() == "org.jetbrains.annotations.Nullable" }
    val notNull : Boolean = annotationMirrors.any { it.annotationType.toString() == "org.jetbrains.annotations.NotNull" }
}