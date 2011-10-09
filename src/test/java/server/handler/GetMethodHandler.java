package server.handler;

import com.github.seratch.httpilot.request.Method;

public class GetMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.GET;
	}

}
