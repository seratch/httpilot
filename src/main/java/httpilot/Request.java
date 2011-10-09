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
package httpilot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Request {

	public Request() {
	}

	public Request(String url) {
		setUrl(url);
	}

	public Request(String url, String charset) {
		setUrl(url);
		setCharset(charset);
	}

	public Request(String url, Map<String, Object> formParams) {
		setUrl(url);
		setFormParams(formParams);
	}

	public HttpURLConnection toHttpURLConnection(Method method)
			throws IOException {
		if (method.equals(Method.GET) && getQueryParams() != null
				&& getQueryParams().size() > 0) {
			for (String key : getQueryParams().keySet()) {
				String param = key + "=" + getQueryParams().get(key);
				url += (url.contains("?") ? "&" : "?") + param;
			}
		}
		URL urlObj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
		conn.setConnectTimeout(connectTimeoutMillis);
		conn.setReadTimeout(readTimeoutMillis);
		conn.setRequestMethod(method.toString());
		conn.setRequestProperty("User-Agent", userAgent);
		for (String headerKey : headers.keySet()) {
			conn.setRequestProperty(headerKey, headers.get(headerKey));
		}
		return conn;
	}

	private boolean enableThrowingIOException = false;

	private String url;

	private int connectTimeoutMillis = 3000;

	private int readTimeoutMillis = 10000;

	private String referrer;

	private String userAgent = "HTTPilot (https://github.com/seratch/httpilot)";

	private String charset = "UTF-8";

	private Map<String, String> headers = new HashMap<String, String>();

	private Map<String, Object> queryParams = new HashMap<String, Object>();

	private RequestBody requestBody = new RequestBody(this);

	private Map<String, Object> formParams = new HashMap<String, Object>();

	private List<FormData> multipartFormData = new ArrayList<FormData>();

	public boolean isEnableThrowingIOException() {
		return enableThrowingIOException;
	}

	public void setEnableThrowingIOException(boolean enableThrowingIOException) {
		this.enableThrowingIOException = enableThrowingIOException;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(int connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public int getReadTimeoutMillis() {
		return readTimeoutMillis;
	}

	public void setReadTimeoutMillis(int readTimeoutMillis) {
		this.readTimeoutMillis = readTimeoutMillis;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

	public RequestBody getRequestBody() {
		return requestBody;
	}

	public void setBody(byte[] body, String contentType) {
		this.requestBody.setBody(body, contentType);
	}

	public byte[] getSpecifiedBody() {
		return requestBody.getSpecifiedBody();
	}

	public String getSpecifiedContentType() {
		return requestBody.getSpecifiedContentType();
	}

	public Map<String, Object> getFormParams() {
		return formParams;
	}

	public void setFormParams(Map<String, Object> formParams) {
		this.formParams = formParams;
	}

	public List<FormData> getMultipartFormData() {
		return multipartFormData;
	}

	public void setMultipartFormData(List<FormData> multipartFormData) {
		this.multipartFormData = multipartFormData;
	}

}
