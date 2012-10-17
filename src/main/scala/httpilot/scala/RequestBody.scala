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

case class RequestBody(underlying: httpilot.RequestBody) {

  def bytes() = underlying.getBytes

  def contentType = underlying.getContentType

  def body(body: Array[Byte], contentType: String) = underlying.setBody(body, contentType)

  def contentType(contentType: String) = underlying.setContentType(contentType)

}