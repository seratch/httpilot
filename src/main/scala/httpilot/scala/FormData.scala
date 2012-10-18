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

case class FormData(name: String, bytes: Array[Byte] = null, text: TextInput = NoTextInput, file: FileInput = NoFileInput)
    extends httpilot.FormData {

  setName(name)

  (text, file) match {
    case (NoTextInput, NoFileInput) => setBody(body)
    case (_, NoFileInput) => setTextBody(text.textBody, text.charset)
    case (NoTextInput, _) => {
      setFile(file.file)
      setContentType(file.contentType)
    }
  }

  def name(name: String) = setName(name)

  def filename() = getFilename

  def contentType() = getContentType

  def contentType(contentType: String) = setContentType(contentType)

  def body() = getBody()

  def body(body: Array[Byte]) = setBody(body)

  def file(file: java.io.File) = setFile(file)

  def textBody(textBody: String, charset: String) = setTextBody(textBody, charset)

}

case class TextInput(textBody: String, charset: String = "UTF-8") extends httpilot.FormData.TextInput(textBody, charset)

object NoTextInput extends TextInput(null, null)

case class FileInput(file: java.io.File, contentType: String) extends httpilot.FormData.FileInput(file, contentType)

object NoFileInput extends FileInput(null, null)