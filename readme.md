# HTTPilot: A Pretty Simple HTTP/1.1 Client for Java

## Getting Started

```xml
<repositories>
  <repository>
    <id>seratch.github.com releases</id>
    <url>http://seratch.github.com/mvn-repo/releases</url>
  </repository>
  <repository>
    <id>seratch.github.com snapshots</id>
    <url>http://seratch.github.com/mvn-repo/snapshots</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.seratch</groupId>
  <artifactId>httpilot</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

### GET

The following code is an example of sending a GET request.

```java
Request request = new Request("http://example.com/");
Response response = HTTP.get(request);

response.getStatus()   // -> int : 200
response.getHeaders()  // -> Map<String, List<String>> : {null=[HTTP/1.1 200 OK], ETag=["33414 ...
response.getBody()     // -> byte[] : ....
response.getTextBody() // -> String : "<htmll><head>..."
```

The above code will send the following HTTP request.

```
GET / HTTP/1.1
User-Agent: HTTPilot (https://github.com/seratch/httpilot)
Accept-Charset: UTF-8
Host: example.com
Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
Connection: keep-alive

```

The default value for "Accept-Charset" is "UTF-8". Off course, it's possible to specify other encoding values.

```java
Request request = new Request("http://example.com/", "EUC-JP");
```

or

```java
Request request = new Request("http://example.com/");
request.setCharset("EUC-JP");
```

It's also possible to overwrite other headers.

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

The above code will send the following HTTP request.

```
POST /register HTTP/1.1
User-Agent: HTTPilot (https://github.com/seratch/httpilot)
Accept-Charset: UTF-8
Content-Type: application/x-www-form-urlencoded
Host: example.com
Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
Connection: keep-alive
Content-Length: 16

name=Andy&age=20
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

The above code will send the following HTTP request.

```
POST / HTTP/1.1
User-Agent: HTTPilot (https://github.com/seratch/httpilot)
Accept-Charset: UTF-8
Content-Type: multipart/form-data; boundary=----HTTPilotBoundary_1318155510859
Host: example.com
Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
Connection: keep-alive
Content-Length: 12345

------HTTPilotBoundary_1318155510859
Content-Disposition: form-data; name="name"

Andy
------HTTPilotBoundary_1318155510859
Content-Disposition: form-data; name="image"; filename="myface.jpg"
content-type: image/jpeg

....
------HTTPilotBoundary_1318155510859--
```

The following code is an example of setting the message body of the request directly.

```java
Request request = new Request("http://example.com/register");
String xml = "<?xml version="1.0" encoding="UTF-8" standalone="no" ?><user><id>1234</id><name>Andy</name></user>";
request.setBody(xml.getBytes(), "text/xml");
Response response = HTTP.post(request);
```

The above code will send the following HTTP request.

```
POST / HTTP/1.1
User-Agent: HTTPilot (https://github.com/seratch/httpilot)
Accept-Charset: UTF-8
Content-Type: text/xml
Host: example.com
Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
Connection: keep-alive
Content-Length: 98

<?xml version="1.0" encoding="UTF-8" standalone="no" ?><user><id>1234</id><name>Andy</name></user>
```

### PUT

The same as sending POST request.

```java
Request request = new Request("http://example.com/register");
String xml = "{\"id\": \"12345\, \"name\": \"Andy\"}";
request.setBody(xml.getBytes(), "text/json");
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
