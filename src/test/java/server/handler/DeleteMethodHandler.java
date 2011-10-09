package server.handler;

import com.github.seratch.httpilot.request.Method;

public class DeleteMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.DELETE;
	}

}
