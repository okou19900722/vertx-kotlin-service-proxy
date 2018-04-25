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

import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.Vertx
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.core.json.JsonArray
import java.util.ArrayList
import java.util.HashSet
import io.vertx.serviceproxy.ServiceException
import io.vertx.serviceproxy.ServiceExceptionMessageCodec
import model.UserModel
import model.Type
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
class UserServiceVertxEBProxy @JvmOverloads constructor(private val _vertx: Vertx, private val _address: String, private val _options: DeliveryOptions? = null) {
    private var closed: Boolean = false

    init {
        try {
            this._vertx.eventBus().registerDefaultCodec(ServiceException::class.java,
                    ServiceExceptionMessageCodec())
        } catch (ex: IllegalStateException) {
        }

    }

    fun testByte(b: Byte) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("b", b)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testByte")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testChar(c: Char) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("c", c.toInt())
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testChar")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testCharacter(c: Char?) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("c", if (c == null) null else c as Int)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testCharacter")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testShort(s: Short) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("s", s)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testShort")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testInteger(i: Int) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("i", i)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testInteger")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testLong(l: Long) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("l", l)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testLong")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testDouble(d: Double) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("d", d)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testDouble")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testFloat(f: Float) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("f", f)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testFloat")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testBoolean(b: Boolean) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("b", b)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testBoolean")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testString(s: String) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("s", s)
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testString")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testEnum(t: Type?) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("t", if (t == null) null else t!!.toString())
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testEnum")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testDataObject(model: UserModel?) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("model", if (model == null) null else model!!.toJson())
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testDataObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListDataObject(list: List<UserModel>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        JsonArray(list.asSequence().map(UserModel::toJson).toList())
        _json.put("list", JsonArray(list.map(UserModel::toJson)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListDataObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListJsonObject(list: List<JsonObject>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListJsonObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListJsonArray(list: List<JsonArray>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListJsonArray")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListString(list: List<String>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListString")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListInteger(list: List<Int>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListInteger")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListByte(list: List<Byte>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListByte")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListCharacter(list: List<Char>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListCharacter")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListLong(list: List<Long>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListLong")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListShort(list: List<Short>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListShort")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListDouble(list: List<Double>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListDouble")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListFloat(list: List<Float>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListFloat")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testListBoolean(list: List<Boolean>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("list", JsonArray(list))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testListBoolean")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetDataObject(set: Set<UserModel>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(set.map(UserModel::toJson)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetDataObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetJsonObject(set: Set<JsonObject>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetJsonObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetJsonArray(set: Set<JsonArray>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetJsonArray")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetString(set: Set<String>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetString")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetInteger(set: Set<Int>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetInteger")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetByte(set: Set<Byte>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetByte")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetCharacter(set: Set<Char>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetCharacter")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetLong(set: Set<Long>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetLong")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetShort(set: Set<Short>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetShort")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetDouble(set: Set<Double>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetDouble")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetFloat(set: Set<Float>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetFloat")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testSetBoolean(set: Set<Boolean>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("set", JsonArray(ArrayList(set)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testSetBoolean")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapString(map: Map<String, String>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapString")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapJsonObject(map: Map<String, JsonObject>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapJsonObject")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapJsonArray(map: Map<String, JsonArray>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapJsonArray")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapInteger(map: Map<String, Int>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapInteger")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapByte(map: Map<String, Byte>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapByte")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapCharacter(map: Map<String, Char>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapCharacter")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapLong(map: Map<String, Long>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapLong")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapShort(map: Map<String, Short>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapShort")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapDouble(map: Map<String, Double>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapDouble")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapFloat(map: Map<String, Float>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapFloat")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun testMapBoolean(map: Map<String, Boolean>) {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        val _json = JsonObject()
        _json.put("map", JsonObject(convertMap(map)))
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "testMapBoolean")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }

    fun test4(handler: Handler<AsyncResult<UserModel>>) {
        if (closed) {
            handler.handle(Future.failedFuture(IllegalStateException("Proxy is closed")))
            return
        }
        val _json = JsonObject()
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "test4")
        _vertx.eventBus().send<JsonObject>(_address, _json, _deliveryOptions) { res ->
            if (res.failed()) {
                handler.handle(Future.failedFuture(res.cause()))
            } else {
                handler.handle(Future.succeededFuture(res.result().body()?.let { UserModel(it) }))
            }
        }
    }

    fun test5(handler: Handler<AsyncResult<List<UserModel>>>) {
        if (closed) {
            handler.handle(Future.failedFuture<List<UserModel>>(IllegalStateException("Proxy is closed")))
            return
        }
        val _json = JsonObject()
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "test5")
        _vertx.eventBus().send<JsonArray>(_address, _json, _deliveryOptions) { res ->
            if (res.failed()) {
                handler.handle(Future.failedFuture<List<UserModel>>(res.cause()))
            } else {
                handler.handle(Future.succeededFuture(res.result().body().asSequence().map { it as JsonObject }.map { UserModel(it) }.toList()))
            }
        }
    }

    fun close() {
        if (closed) {
            throw IllegalStateException("Proxy is closed")
        }
        closed = true
        val _json = JsonObject()
        val _deliveryOptions = if (_options != null) DeliveryOptions(_options) else DeliveryOptions()
        _deliveryOptions.addHeader("action", "close")
        _vertx.eventBus().send(_address, _json, _deliveryOptions)
    }


    private fun convertToListChar(arr: JsonArray): List<Char> {
        val list = ArrayList<Char>()
        for (obj in arr) {
            val jobj = obj as Int
            list.add(jobj.toChar())
        }
        return list
    }

    private fun convertToSetChar(arr: JsonArray): Set<Char> {
        val set = HashSet<Char>()
        for (obj in arr) {
            val jobj = obj as Int
            set.add(jobj.toChar())
        }
        return set
    }
    @Suppress("UNCHECKED_CAST")
    private fun <T> convertMap(map: Map<String, Any>): Map<String, T> {
        if (map.isEmpty()) {
            return map as Map<String, T>
        }

        val elem = map.values.stream().findFirst().get()
        return if (elem !is Map<*, *> && elem !is List<*>) {
            map as Map<String, T>
        } else {
            val converter : (Any) -> T
            converter = if (elem is List<*>) {
                { obj -> JsonArray(obj as List<*>) as T }
            } else {
                { obj -> JsonObject(obj as Map<String, *>) as T }
            }
            map.entries.asSequence().associateBy(Map.Entry<String, Any>::key, converter)
//            return (map as Map<String, T>).entries
//                    .stream()
//                    .collect<Map<String, T>, Any>(Collectors.toMap<Entry<String, T>, String, T>({ it.key }) { converter.apply(it) })
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> convertList(list: List<Any>): List<T> {
        if (list.isEmpty()) {
            return list as List<T>
        }

        val elem = list[0]
        return if (elem !is Map<*, *> && elem !is List<*>) {
            list as List<T>
        } else {
            val converter : (Any) -> T
            converter = if (elem is List<*>) {
                { obj -> JsonArray(obj as List<*>) as T }
            } else {
                { obj -> JsonObject(obj as Map<String, *>) as T }
            }
            list.asSequence().map(converter).toList()
        }
    }

    private fun <T> convertSet(list: List<Any>): Set<T> {
        return HashSet(convertList(list))
    }
}