package server.handler;

import com.github.seratch.httpilot.request.Method;

public class HeadMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.HEAD;
	}

}