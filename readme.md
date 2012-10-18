# HTTPilot: A Pretty Simple HTTP/1.1 Client for Java/Scala

HTTPilot is a pretty simple HTTP/1.1 client library which is written in Java.

## Getting Started

### Scala via xsbt

```scala
libraryDependencies += "com.github.seratch" %% "httpliot-scala" % "0.3.1"
```

### Java via Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>httpilot</artifactId>
    <version>0.3.1</version>
  </dependency>
</dependencies>
```

### Groovy via Grape

```groovy
@Grab('com.github.seratch:httpilot:0.3.1')

import httpilot.*;
response = HTTP.get(new Request("http://seratch.github.com"));
println(response.getTextBody());
```


## Usage

### Scala Usage

GET:

```scala
import httpilot.scala._
val response = HTTP.get("http://example.com")

scala> val status = response.status
status: Int = 200

scala> val headers = response.headers
headers: Map[String,String] = Map(null -> HTTP/1.1 200 OK, ETag -> "2800e-1b46-4c7d1dcaf9817", Date -> Wed, 17 Oct 2012 15:03:39 GMT, Content-Length -> 6982, Last-Modified -> Wed, 22 Aug 2012 02:54:31 GMT, Content-Type -> text/html; charset=UTF-8, Connection -> close, Accept-Ranges -> bytes, Server -> Apache/2.2.22 (Amazon))

scala> val html = response.asString
html: java.lang.String = "<!DOCTYPE html PUBLIC "-//W3C//DTD ...

scala> val bin = response.asBytes
bin: Array[Byte] = Array(60, 33, 68, 79, 67, ...
```

POST/PUT:

```scala
val response = HTTP.post("http://example.com/register", "aa=bb")

val response = HTTP.post("http://example.com/register", Map("aaa" -> "bb"))
```

DELETE/OPTIONS/HEAD/TRACE:

Same as Java/Groovy Usage.


## Java/Groovy Usage

### GET

The following code is an example of sending a GET request.

```java
import httpilot.*;
Request request = new Request("http://example.com/");
Response response = HTTP.get(request);

response.getStatus()   // -> int : 200
response.getHeaders()  // -> Map<String, String> : {null=HTTP/1.1 200 OK, ETag="33414 ...
response.getHeaderFields()  // -> Map<String, List<String>> : {null=[HTTP/1.1 200 OK], ETag=["33414 ...
response.getBody()     // -> byte[] : ....
response.getTextBody() // -> String : "<htmll><head>..."
```

It's also possible to append the query string with a Map object.

```java
Request request = new Request("http://example.com/?name=Andy");
Map<String, Object> queryParams = new HashMap<String, Object>();
queryParams.put("age", 20);
request.setQueryParams(queryParams);
````

The default value for "Accept-Charset" is "UTF-8". Needless to say, it's possible to specify other encoding values.

```java
Request request = new Request("http://example.com/", "EUC-JP");
```

or

```java
Request request = new Request("http://example.com/");
request.setCharset("EUC-JP");
```

It's also possible to overwrite other headerFields.

```java
request.setHeader("Authorization", "OAuth oauth_consumer_key=...")
```

### POST

The following code is an example of sending a POST request.

```java
Map<String, Object> formParams = new HashMap<String, Object>();
formParams.put("name", "Andy");
formParams.put("age", 20);
// Request request = new Request("http://example.com/register", formParams);
Request request = new Request("http://example.com/register");
request.setFormParams(formParams);
Response response = HTTP.post(request);
```

"multipart/form-data" is also available.

```java
List<FormData> data = new ArrayList<FormData>();
data.add(new FormData("name", new TextInput("Andy", "UTF-8")));
data.add(new FormData("image", new FileInput(new File("./myface.jpg"), "myface.jpg"), "image/jpeg"));
Request request = new Request("http://example.com/register");
request.setMultipartFormData(data);
Response response = HTTP.post(request);
```

The following code is an example of setting the message body of the request directly.

```java
Request request = new Request("http://example.com/register");
String xml = "<?xml version="1.0" encoding="UTF-8" standalone="no" ?><user><id>1234</id><name>Andy</name></user>";
request.setBody(xml.getBytes(), "text/xml");
Response response = HTTP.post(request);
```

### PUT

The same as sending POST request.

```java
Request request = new Request("http://example.com/register");
String json = "{\"id\": \"12345\, \"name\": \"Andy\"}";
request.setBody(json.getBytes(), "text/json");
Response response = HTTP.put(request);
```

### DELETE

```java
Request request = new Request("http://example.com/user/12345");
Response response = HTTP.delete(request);
```

### OPTIONS

```java
Request request = new Request("http://example.com/blog/12345");
Response response = HTTP.options(request);

response.getHeaders().get("Allow").toString() // -> "[GET, HEAD, OPTIONS, TRACE]"
```

### HEAD

```java
Request request = new Request("http://example.com/");
Response response = HTTP.head(request);
```

### TRACE

```java
Request request = new Request("http://example.com/");
Response response = HTTP.trace(request);

response.getTextBody();
// TRACE / HTTP/1.1
// User-Agent: HTTPilot (https://github.com/seratch/httpilot)
// Accept-Charset: UTF-8
// Host: example.com
// Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
// Connection: keep-alive
// 
```

## License


```java
/*
 * Copyright 2011 Kazuhiro Sera
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
```
