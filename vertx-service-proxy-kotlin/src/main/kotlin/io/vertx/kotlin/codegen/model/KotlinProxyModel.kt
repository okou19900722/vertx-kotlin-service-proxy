package io.vertx.kotlin.codegen.model

import io.vertx.codegen.Model
import io.vertx.codegen.ModuleInfo
import io.vertx.codegen.ProxyModel
import javax.lang.model.element.Element

/**
 * Create by yan on 2018/2/9 14:40
 */
class KotlinProxyModel(private val proxyModel: ProxyModel) : Model {
    override fun getElement(): Element = proxyModel.element

    override fun getKind(): String = "proxy"

    override fun getModule(): ModuleInfo = proxyModel.module

    override fun getFqn(): String = proxyModel.fqn
}