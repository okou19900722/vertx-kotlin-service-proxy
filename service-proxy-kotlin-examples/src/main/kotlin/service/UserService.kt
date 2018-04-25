package service

import io.vertx.codegen.annotations.Fluent
import io.vertx.codegen.annotations.ProxyClose
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import model.Type
import model.UserModel

annotation class MethodAaa
annotation class ParamAaa
@ProxyGen
interface UserService {
    @Fluent
    fun test(list : List<Int>) : UserService?
//    fun test(list : MutableList<out UserModel>)
//    fun testByte(b : Byte)
//    fun testChar(c: Char)
//    fun testCharacter(c: Char)
//    fun testShort(s: Short)
//    fun testInteger(i: Int)
//    fun testLong(l: Long)
//    fun testDouble(d: Double)
//    fun testFloat(f: Float)
//    fun testBoolean(b: Boolean)
//    fun testString(s: String)
//    fun testEnum(t: Type)
//    fun testDataObject(model: UserModel)
//
//
//
//    fun testListDataObject(list: List<UserModel>)
//
////    fun testListJsonObject(list: List<JsonObject>)
////    fun testListJsonArray(list: List<JsonArray>)
//    fun testListString(list: List<String>)
//    fun testListInteger(list: List<Int>)
//    fun testListByte(list: List<Byte>)
//    fun testListCharacter(list: List<Char>)
//    fun testListLong(list: List<Long>)
//    fun testListShort(list: List<Short>)
//    fun testListDouble(list: List<Double>)
//    fun testListFloat(list: List<Float>)
//    fun testListBoolean(list: List<Boolean>)
//
//
////    fun testSetDataObject(set: Set<UserModel>)
//
////    fun testSetJsonObject(set: Set<JsonObject>)
////    fun testSetJsonArray(set: Set<JsonArray>)
//    fun testSetString(set: Set<String>)
//    fun testSetInteger(set: Set<Int>)
//    fun testSetByte(set: Set<Byte>)
//    fun testSetCharacter(set: Set<Char>)
//    fun testSetLong(set: Set<Long>)
//    fun testSetShort(set: Set<Short>)
//    fun testSetDouble(set: Set<Double>)
//    fun testSetFloat(set: Set<Float>)
//    fun testSetBoolean(set: Set<Boolean>)
//
//
//
//    fun testMapString(map: Map<String, String>)
//
////    fun testMapJsonObject(map: Map<String, JsonObject>)
////    fun testMapJsonArray(map: Map<String, JsonArray>)
//    fun testMapInteger(map: Map<String, Int>)
//    fun testMapByte(map: Map<String, Byte>)
//    fun testMapCharacter(map: Map<String, Char>)
//    fun testMapLong(map: Map<String, Long>)
//    fun testMapShort(map: Map<String, Short>)
//    fun testMapDouble(map: Map<String, Double>)
//    fun testMapFloat(map: Map<String, Float>)
//    fun testMapBoolean(map: Map<String, Boolean>)
//
//
////    fun test4(handler: Handler<AsyncResult<UserModel>>)
//
////    fun test5(handler: Handler<AsyncResult<List<UserModel>>>)
//
//    @ProxyClose
    fun close()
}