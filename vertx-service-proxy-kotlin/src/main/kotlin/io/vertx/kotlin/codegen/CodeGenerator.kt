package io.vertx.kotlin.codegen

import io.vertx.codegen.Template
import java.io.Serializable

/**
 * Create by yan on 2017/12/25 13:53
 */
class CodeGenerator(
        val name: String,
        val kind: String,
        val incremental: Boolean,
        val filename: String,
        val templateFilename: String) {
    internal var template: Template? = null
    internal var filenameExpr: Serializable? = null
}