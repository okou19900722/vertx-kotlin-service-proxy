package io.vertx.kotlin.codegen

import javax.lang.model.element.Element

/**
 * Create by yan on 2017/12/25 14:10
 */
class GenException(internal val element: Element?, internal val msg: String?) : RuntimeException(msg) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val exception = other as GenException?

        if (if (element != null) element != exception!!.element else exception!!.element != null) return false
        return !if (msg != null) msg != exception.msg else exception.msg != null

    }

    override fun hashCode(): Int {
        var result = element?.hashCode() ?: 0
        result = 31 * result + (msg?.hashCode() ?: 0)
        return result
    }
}
