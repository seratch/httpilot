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

case class Response(underlying: httpilot.Response) {

  def status() = underlying.getStatus

  def status(status: Int) = underlying.setStatus(status)

  def headers() = underlying.getHeaders.asScala

  def headers(headers: Map[String, String]) = underlying.setHeaders(headers.asJava)

  def headerFields(): Map[String, Seq[String]] = underlying.getHeaderFields.asScala.map {
    case (k, v) => (k, v.asScala.toSeq)
  }.toMap

  def headerFields(fields: Map[String, Seq[String]]) = {
    val toValueAsJava: Map[String, java.util.List[String]] = fields.map {
      case (k: String, v: Seq[_]) => (k, v.asJava.asInstanceOf[java.util.List[String]])
    }
    underlying.setHeaderFields(toValueAsJava.asJava)
  }

  def charset() = underlying.getCharset

  def charset(charset: String) = underlying.setCharset(charset)

  def body() = underlying.getBody

  def body(body: Array[Byte]) = underlying.setBody(body)

  def textBody() = underlying.getTextBody

}
