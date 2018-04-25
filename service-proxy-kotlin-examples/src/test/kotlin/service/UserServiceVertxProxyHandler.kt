/*
* Copyright 2014 Red Hat, Inc.
*
* Red Hat licenses this file to you under the Apache License, version 2.0
* (the "License"); you may not use this file except in compliance with the
* License. You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package service

import io.vertx.core.Vertx
import io.vertx.core.Handler
import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.core.json.JsonArray
import java.util.ArrayList
import java.util.HashSet
import java.util.stream.Collectors
import io.vertx.serviceproxy.ProxyHandler
import io.vertx.serviceproxy.ServiceException
import io.vertx.serviceproxy.ServiceExceptionMessageCodec
import model.UserModel
import model.Type

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
class UserServiceVertxProxyHandler(private val vertx: Vertx, private val service: UserService, topLevel: Boolean, private val timeoutSeconds: Long) : ProxyHandler() {
    private val timerID: Long
    private var lastAccessed: Long = 0

    @JvmOverloads constructor(vertx: Vertx, service: UserService, timeoutInSecond: Long = DEFAULT_CONNECTION_TIMEOUT) : this(vertx, service, true, timeoutInSecond) {}

    init {
        try {
            this.vertx.eventBus().registerDefaultCodec(ServiceException::class.java,
                    ServiceExceptionMessageCodec())
        } catch (ex: IllegalStateException) {
        }

        if (timeoutSeconds > -1 && !topLevel) {
            var period = timeoutSeconds * 1000 / 2
            if (period > 10000) {
                period = 10000
            }
            this.timerID = vertx.setPeriodic(period) { this.checkTimedOut(it) }
        } else {
            this.timerID = -1
        }
        accessed()
    }

    fun registerHandler(address: String): MessageConsumer<JsonObject> {
        val consumer = vertx.eventBus().consumer<JsonObject>(address).handler(this)
        this.consumer = consumer
        return consumer
    }

    private fun checkTimedOut(id: Long) {
        val now = System.nanoTime()
        if (now - lastAccessed > timeoutSeconds * 1000000000) {
            service.close()
            close()
        }
    }

    override fun close() {
        if (timerID > -1) {
            vertx.cancelTimer(timerID)
        }
        super.close()
    }

    private fun accessed() {
        this.lastAccessed = System.nanoTime()
    }

    override fun handle(msg: Message<JsonObject>) {
        try {
            val json = msg.body()
            val action = msg.headers().get("action") ?: throw IllegalStateException("action not specified")
            accessed()
//            when (action) {
//                "testByte" -> {
//                    service.testByte(if (json.getValue("b") == null) null else json.getLong("b")!!.toByte())
//                }
//                "testChar" -> {
//                    service.testChar(if (json.getInteger("c") == null) null else (json.getInteger("c") as Int).toChar())
//                }
//                "testCharacter" -> {
//                    service.testCharacter(if (json.getInteger("c") == null) null else (json.getInteger("c") as Int).toChar())
//                }
//                "testShort" -> {
//                    service.testShort(if (json.getValue("s") == null) null else json.getLong("s")!!.toShort())
//                }
//                "testInteger" -> {
//                    service.testInteger(if (json.getValue("i") == null) null else json.getLong("i")!!.toInt())
//                }
//                "testLong" -> {
//                    service.testLong(if (json.getValue("l") == null) null else json.getLong("l")!!.toLong())
//                }
//                "testDouble" -> {
//                    service.testDouble(if (json.getValue("d") == null) null else json.getDouble("d")!!.toDouble())
//                }
//                "testFloat" -> {
//                    service.testFloat(if (json.getValue("f") == null) null else json.getDouble("f")!!.toFloat())
//                }
//                "testBoolean" -> {
//                    service.testBoolean(json.getValue("b") as Boolean)
//                }
//                "testString" -> {
//                    service.testString(json.getValue("s") as java.lang.String)
//                }
//                "testEnum" -> {
//                    service.testEnum(if (json.getString("t") == null) null else Type.valueOf(json.getString("t")))
//                }
//                "testDataObject" -> {
//                    service.testDataObject(if (json.getJsonObject("model") == null) null else UserModel(json.getJsonObject("model")))
//                }
//                "testListDataObject" -> {
//                    service.testListDataObject(json.getJsonArray("list").stream().map<Any> { o -> UserModel(o as JsonObject) }.collect<R, A>(Collectors.toList<T>()))
//                }
//                "testListJsonObject" -> {
//                    service.testListJsonObject(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListJsonArray" -> {
//                    service.testListJsonArray(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListString" -> {
//                    service.testListString(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListInteger" -> {
//                    service.testListInteger(json.getJsonArray("list").stream().map { o -> (o as Number).toInt() }.collect<R, A>(Collectors.toList<T>()))
//                }
//                "testListByte" -> {
//                    service.testListByte(json.getJsonArray("list").stream().map { o -> (o as Number).toByte() }.collect<R, A>(Collectors.toList<T>()))
//                }
//                "testListCharacter" -> {
//                    service.testListCharacter(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListLong" -> {
//                    service.testListLong(json.getJsonArray("list").stream().map { o -> (o as Number).toLong() }.collect<R, A>(Collectors.toList<T>()))
//                }
//                "testListShort" -> {
//                    service.testListShort(json.getJsonArray("list").stream().map { o -> (o as Number).toShort() }.collect<R, A>(Collectors.toList<T>()))
//                }
//                "testListDouble" -> {
//                    service.testListDouble(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListFloat" -> {
//                    service.testListFloat(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testListBoolean" -> {
//                    service.testListBoolean(convertList<T>(json.getJsonArray("list").list))
//                }
//                "testSetDataObject" -> {
//                    service.testSetDataObject(json.getJsonArray("set").stream().map<Any> { o -> UserModel(o as JsonObject) }.collect<R, A>(Collectors.toSet<T>()))
//                }
//                "testSetJsonObject" -> {
//                    service.testSetJsonObject(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetJsonArray" -> {
//                    service.testSetJsonArray(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetString" -> {
//                    service.testSetString(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetInteger" -> {
//                    service.testSetInteger(json.getJsonArray("set").stream().map { o -> (o as Number).toInt() }.collect<R, A>(Collectors.toSet<T>()))
//                }
//                "testSetByte" -> {
//                    service.testSetByte(json.getJsonArray("set").stream().map { o -> (o as Number).toByte() }.collect<R, A>(Collectors.toSet<T>()))
//                }
//                "testSetCharacter" -> {
//                    service.testSetCharacter(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetLong" -> {
//                    service.testSetLong(json.getJsonArray("set").stream().map { o -> (o as Number).toLong() }.collect<R, A>(Collectors.toSet<T>()))
//                }
//                "testSetShort" -> {
//                    service.testSetShort(json.getJsonArray("set").stream().map { o -> (o as Number).toShort() }.collect<R, A>(Collectors.toSet<T>()))
//                }
//                "testSetDouble" -> {
//                    service.testSetDouble(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetFloat" -> {
//                    service.testSetFloat(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testSetBoolean" -> {
//                    service.testSetBoolean(convertSet<T>(json.getJsonArray("set").list))
//                }
//                "testMapString" -> {
//                    service.testMapString(convertMap<T>(json.getJsonObject("map").map))
//                }
//                "testMapJsonObject" -> {
//                    service.testMapJsonObject(convertMap<T>(json.getJsonObject("map").map))
//                }
//                "testMapJsonArray" -> {
//                    service.testMapJsonArray(convertMap<T>(json.getJsonObject("map").map))
//                }
//                "testMapInteger" -> {
//                    service.testMapInteger(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toInt() }))
//                }
//                "testMapByte" -> {
//                    service.testMapByte(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toByte() }))
//                }
//                "testMapCharacter" -> {
//                    service.testMapCharacter(convertMap<T>(json.getJsonObject("map").map))
//                }
//                "testMapLong" -> {
//                    service.testMapLong(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toLong() }))
//                }
//                "testMapShort" -> {
//                    service.testMapShort(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toShort() }))
//                }
//                "testMapDouble" -> {
//                    service.testMapDouble(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toDouble() }))
//                }
//                "testMapFloat" -> {
//                    service.testMapFloat(json.getJsonObject("map").map.entries.stream().collect<R, A>(Collectors.toMap<T, K, U>(Function<T, K> { it.key }) { entry -> (entry.getValue() as java.lang.Number).toFloat() }))
//                }
//                "testMapBoolean" -> {
//                    service.testMapBoolean(convertMap<T>(json.getJsonObject("map").map))
//                }
//                "test4" -> {
//                    service.test4 { res ->
//                        if (res.failed()) {
//                            if (res.cause() is ServiceException) {
//                                msg.reply(res.cause())
//                            } else {
//                                msg.reply(ServiceException(-1, res.cause().getMessage()))
//                            }
//                        } else {
//                            msg.reply(if (res.result() == null) null else res.result().toJson())
//                        }
//                    }
//                }
//                "test5" -> {
//                    service.test5 { res ->
//                        if (res.failed()) {
//                            if (res.cause() is ServiceException) {
//                                msg.reply(res.cause())
//                            } else {
//                                msg.reply(ServiceException(-1, res.cause().getMessage()))
//                            }
//                        } else {
//                            msg.reply(JsonArray(res.result().stream().map(??? { UserModel.toJson() }).collect(Collectors.toList<T>())))
//                        }
//                    }
//                }
//                "close" -> {
//                    service.close()
//                    close()
//                }
//                else -> {
//                    throw IllegalStateException("Invalid action: " + action)
//                }
//            }
        } catch (t: Throwable) {
            msg.reply(ServiceException(500, t.message))
            throw t
        }

    }

//    private fun <T> createHandler(msg: Message<*>): Handler<AsyncResult<T>> {
//        return { res ->
//            if (res.failed()) {
//                if (res.cause() is ServiceException) {
//                    msg.reply(res.cause())
//                } else {
//                    msg.reply(ServiceException(-1, res.cause().message))
//                }
//            } else {
//                if (res.result() != null && res.result().javaClass.isEnum()) {
//                    msg.reply((res.result() as Enum<*>).name)
//                } else {
//                    msg.reply(res.result())
//                }
//            }
//        }
//    }
//
//    private fun <T> createListHandler(msg: Message<*>): Handler<AsyncResult<List<T>>> {
//        return { res ->
//            if (res.failed()) {
//                if (res.cause() is ServiceException) {
//                    msg.reply(res.cause())
//                } else {
//                    msg.reply(ServiceException(-1, res.cause().message))
//                }
//            } else {
//                msg.reply(JsonArray(res.result()))
//            }
//        }
//    }
//
//    private fun <T> createSetHandler(msg: Message<*>): Handler<AsyncResult<Set<T>>> {
//        return { res ->
//            if (res.failed()) {
//                if (res.cause() is ServiceException) {
//                    msg.reply(res.cause())
//                } else {
//                    msg.reply(ServiceException(-1, res.cause().message))
//                }
//            } else {
//                msg.reply(JsonArray(ArrayList<T>(res.result())))
//            }
//        }
//    }
//
//    private fun createListCharHandler(msg: Message<*>): Handler<AsyncResult<List<Char>>> {
//        return { res ->
//            if (res.failed()) {
//                if (res.cause() is ServiceException) {
//                    msg.reply(res.cause())
//                } else {
//                    msg.reply(ServiceException(-1, res.cause().message))
//                }
//            } else {
//                val arr = JsonArray()
//                for (chr in res.result()) {
//                    arr.add(chr as Int)
//                }
//                msg.reply(arr)
//            }
//        }
//    }
//
//    private fun createSetCharHandler(msg: Message<*>): Handler<AsyncResult<Set<Char>>> {
//        return { res ->
//            if (res.failed()) {
//                if (res.cause() is ServiceException) {
//                    msg.reply(res.cause())
//                } else {
//                    msg.reply(ServiceException(-1, res.cause().message))
//                }
//            } else {
//                val arr = JsonArray()
//                for (chr in res.result()) {
//                    arr.add(chr as Int)
//                }
//                msg.reply(arr)
//            }
//        }
//    }

    private fun <T> convertMap(map: Map<*, *>): Map<String, T> {
        return map as Map<String, T>
    }

    private fun <T> convertList(list: List<*>): List<T> {
        return list as List<T>
    }

    private fun <T> convertSet(list: List<*>): Set<T> {
        return HashSet(list as List<T>)
    }

    companion object {

        val DEFAULT_CONNECTION_TIMEOUT = (5 * 60).toLong() // 5 minutes
    }
}