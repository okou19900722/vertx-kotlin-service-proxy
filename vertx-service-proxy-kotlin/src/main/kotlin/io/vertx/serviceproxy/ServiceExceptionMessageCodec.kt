package io.vertx.serviceproxy

import io.netty.util.CharsetUtil
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.JsonObject

/**
 * A MessageCodec for ServiceException
 *
 * @author [Dan O'Reilly](mailto:oreilldf@gmail.com)
 */
class ServiceExceptionMessageCodec : MessageCodec<ServiceException, ServiceException> {

    override fun encodeToWire(buffer: Buffer, body: ServiceException) {
        buffer.appendInt(body.failureCode())
        if (body.message == null) {
            buffer.appendByte(0.toByte())
        } else {
            buffer.appendByte(1.toByte())
            val encoded = body.message.toByteArray(CharsetUtil.UTF_8)
            buffer.appendInt(encoded.size)
            buffer.appendBytes(encoded)
        }
        body.debugInfo.writeToBuffer(buffer)
    }
    private val zero : Byte = 0
    override fun decodeFromWire(pos: Int, buffer: Buffer): ServiceException {
        var position = pos
        val failureCode = buffer.getInt(position)
        position += 4
        val isNull = buffer.getByte(position) == zero
        position++
        val message: String?
        if (!isNull) {
            val strLength = buffer.getInt(position)
            position += 4
            val bytes = buffer.getBytes(position, position + strLength)
            message = String(bytes, CharsetUtil.UTF_8)
            position += strLength
        } else {
            message = null
        }
        val debugInfo = JsonObject()
        debugInfo.readFromBuffer(position, buffer)
        return ServiceException(failureCode, message!!, debugInfo)
    }

    override fun transform(exception: ServiceException): ServiceException {
        return exception
    }

    override fun name(): String {
        return "ServiceException"
    }

    override fun systemCodecID(): Byte {
        return -1
    }
}

