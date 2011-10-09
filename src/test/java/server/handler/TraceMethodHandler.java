package server.handler;

import com.github.seratch.httpilot.request.Method;

public class TraceMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.TRACE;
	}

}
