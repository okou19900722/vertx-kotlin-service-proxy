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

package service;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHandler;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import java.util.List;
import service.UserService;

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
//@SuppressWarnings({"unchecked", "rawtypes"})
abstract class UserServiceVertxKtProxyHandler(val vertx : Vertx, val service : UserService, val topLevel : Boolean = true, val timeoutSeconds : Long = 300) : ProxyHandler() {

    private val timerID : Long
    private var lastAccessed: Long = 0
    init {
        this.vertx.eventBus().registerDefaultCodec(ServiceException::class.java, ServiceExceptionMessageCodec())
        if (timeoutSeconds > -1 && !topLevel) {
            var period = timeoutSeconds * 1000 / 2
            if (period > 10000) {
                period = 10000
            }
            this.timerID = vertx.setPeriodic(period) {
                this.checkTimedOut(it)
            }
        } else {
            this.timerID = -1
        }
        accessed()
    }
    private fun checkTimedOut(id: Long) {
        val now = System.nanoTime()
        if (now - lastAccessed > timeoutSeconds * 1000000000) {
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
}