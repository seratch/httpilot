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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class RequestBody {

	private static final String CRLF = "\r\n";

	private Request request;

	private String specifiedContentType = null;

	private byte[] specifiedBody = null;

	public RequestBody(Request request) {
		this.request = request;
	}

	public byte[] getSpecifiedBody() {
		return specifiedBody;
	}

	public String getSpecifiedContentType() {
		return specifiedContentType;
	}

	public void setBody(byte[] body, String contentType) {
		this.specifiedBody = body;
		this.specifiedContentType = contentType;
	}

	public byte[] asApplicationXWwwFormUrlencoded() {
		Map<String, Object> formParams = request.getFormParams();
		StringBuilder sb = new StringBuilder();
		for (String key : request.getFormParams().keySet()) {
			Object value = formParams.get(key);
			if (value != null) {
				sb.append(urlEncode(key));
				sb.append("=");
				sb.append(urlEncode(formParams.get(key).toString()));
			}
			sb.append("&");
		}
		return sb.toString().replaceFirst("&$", "").getBytes();
	}

	public byte[] asMultipart(String boundary) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			for (FormData formData : request.getMultipartFormData()) {
				StringBuilder sb = new StringBuilder();
				sb.append("--");
				sb.append(boundary);
				sb.append(CRLF);
				sb.append("Content-Disposition: form-data; name=\"");
				sb.append(formData.getName());
				sb.append("\"");
				if (formData.getFilename() != null) {
					sb.append("; filename=\"");
					sb.append(formData.getFilename());
					sb.append("\"");
				}
				sb.append(CRLF);
				if (formData.getContentType() != null) {
					sb.append("content-type: ");
					sb.append(formData.getContentType());
					sb.append(CRLF);
				}
				sb.append(CRLF);
				os.write(sb.toString().getBytes());
				for (int i = 0; i < formData.getBody().length; i++) {
					// write bytes to OutputStream directly
					os.write(formData.getBody()[i]);
				}
				os.write(CRLF.getBytes());
			}
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("--");
			sb.append(CRLF);
			os.write(sb.toString().getBytes());
			return os.toByteArray();

		} finally {
			IO.close(os);
		}
	}

	private static String urlEncode(String rawValue) {
		try {
			return URLEncoder.encode(rawValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return rawValue;
	}

}
