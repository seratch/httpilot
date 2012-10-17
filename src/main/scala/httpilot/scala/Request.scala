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

case class Request(url: String) extends httpilot.Request(url) {

  def enableThrowingIOException(enabled: Boolean) = setEnableThrowingIOException(enabled)

  def url(url: String) = setUrl(url)

  def connectTimeoutMillis() = getConnectTimeoutMillis

  def connectTimeoutMillis(millis: Int) = setConnectTimeoutMillis(millis)

  def readTimeoutMillis() = getReadTimeoutMillis

  def readTimeoutMillis(millis: Int) = setReadTimeoutMillis(millis)

  def referer() = getReferer

  def referer(referer: String) = setReferer(referer)

  def userAgent() = getUserAgent

  def userAgent(ua: String) = setUserAgent(ua)

  def charset() = getCharset

  def charset(charset: String) = setCharset(charset)

  def headerNames() = getHeaderNames.asScala

  def header(name: String) = getHeader(name)

  def header(name: String, value: String) = setHeader(name, value)

  def queryParams() = getQueryParams.asScala

  def queryParams(queryParams: Map[String, Any]) = setQueryParams(queryParams.map {
    case (k, v) => (k, v.asInstanceOf[java.lang.Object])
  }.asJava)

  def requestBody() = RequestBody(getRequestBody)

  def body(body: Array[Byte], contentType: String) = setBody(body, contentType)

  def bodyAsBytes(): Array[Byte] = getRequestBody.getBytes

  def contentType() = requestBody.contentType

  def contentType(contentType: String) = requestBody.contentType(contentType)

  def formParams() = getFormParams.asScala

  def formParams(formParams: Map[String, Any]) = setFormParams(formParams.map {
    case (k, v) => (k, v.asInstanceOf[java.lang.Object])
  }.asJava)

  def multipartFormData() = getMultipartFormData.asScala

  def multipartFormData(formData: List[FormData]) = setMultipartFormData(
    formData.map(f => f.asInstanceOf[httpilot.FormData]).asJava
  )

}
