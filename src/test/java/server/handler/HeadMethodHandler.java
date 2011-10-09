package server.handler;

import httpilot.Method;

public class HeadMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.HEAD;
	}

}