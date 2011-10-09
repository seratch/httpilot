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
package com.github.seratch.httpilot;

import com.github.seratch.httpilot.response.Response;
import java.io.IOException;

public class HTTPIOException extends IOException {

	private static final long serialVersionUID = 1L;

	private String message;

	private Response repsonse;

	public HTTPIOException(String message, Response response) {
		setMessage(message);
		setRepsonse(response);
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getLocalizedMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Response getRepsonse() {
		return repsonse;
	}

	public void setRepsonse(Response repsonse) {
		this.repsonse = repsonse;
	}

}