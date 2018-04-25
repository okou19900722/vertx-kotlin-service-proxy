/*
* Copyright 2014 Red Hat, Inc.
*
* Red Hat licenses this file to you under the Apache License, version 2.0
* (the "License") you may not use this file except in compliance with the
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

import service.UserService
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.Vertx
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.core.json.JsonArray
//import io.vertx.serviceproxy.ProxyHelper
//import io.vertx.serviceproxy.ServiceException
//import io.vertx.serviceproxy.ServiceExceptionMessageCodec

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
//@SuppressWarnings({"unchecked", "rawtypes"})
abstract class UserServiceVertxKtEBProxy(private val vertx: Vertx, private val address : String, private val options : DeliveryOptions? = null) : UserService {
    var closed : Boolean = false 

    /*  override fun test( list : List<Integer>) : UserService  {
          if (closed) {
            throw IllegalStateException("Proxy is closed")
}
    val _json = JsonObject()
    _json.put("list", JsonArray(list))
    val _deliveryOptions = if((options != null))DeliveryOptions(options) else DeliveryOptions()
    _deliveryOptions.addHeader("action", "test")
    vertx.eventBus().send(address, _json, _deliveryOptions)
            return this
          }

    */          /*  override fun close() {
          if (closed) {
            throw IllegalStateException("Proxy is closed")
}
    val _json = JsonObject()
    val _deliveryOptions = if((options != null))DeliveryOptions(options) else DeliveryOptions()
    _deliveryOptions.addHeader("action", "close")
    vertx.eventBus().send(address, _json, _deliveryOptions)
          }

    */      
}