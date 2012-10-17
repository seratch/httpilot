/*
 * Copyright 2012 Kazuhiro Sera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package httpilot.scala

import scala.collection.JavaConverters._
import httpilot.{ HTTP => JavaHTTP, Request => JavaRequest }

object HTTP {

  def get(req: Request) = Response(JavaHTTP.get(req))

  def get(url: String, charset: String = httpilot.Request.DEFAULT_CHARSET) = {
    Response(JavaHTTP.get(new JavaRequest(url, charset)))
  }

  def post(req: Request) = Response(JavaHTTP.post(req))

  def post(url: String, data: String) = {
    Response(JavaHTTP.post(Request(url).body(data.getBytes)))
  }

  def post(url: String, formParams: Map[String, Any]) = {
    Response(JavaHTTP.post(new JavaRequest(url, formParams.map {
      case (k, v) => (k, v.asInstanceOf[java.lang.Object])
    }.asJava)))
  }

  def put(req: Request) = Response(JavaHTTP.put(req))

  def put(url: String, data: String) = {
    Response(JavaHTTP.put(new Request(url).body(data.getBytes)))
  }

  def put(url: String, formParams: Map[String, Any]) = {
    Response(JavaHTTP.put(new JavaRequest(url, formParams.map {
      case (k, v) => (k, v.asInstanceOf[java.lang.Object])
    }.asJava)))
  }

  def delete(req: Request) = Response(JavaHTTP.delete(req))

  def head(req: Request) = Response(JavaHTTP.head(req))

  def options(req: Request) = Response(JavaHTTP.options(req))

  def trace(req: Request) = Response(JavaHTTP.trace(req))

  def request(method: Method, req: Request) = Response(JavaHTTP.request(method, req))

}
