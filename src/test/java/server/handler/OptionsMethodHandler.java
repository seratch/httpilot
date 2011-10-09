package server.handler;

import httpilot.Method;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class OptionsMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.OPTIONS;
	}

	@SuppressWarnings("unchecked")
	public void _handle(Boolean isAllowed, Method method,
	                    Request baseRequest, HttpServletRequest request,
	                    HttpServletResponse response) throws Exception {
		if (isAllowed) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader("Allow", "GET, HEAD, OPTIONS, TRACE");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("おｋ");
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("だｍ");
		}
		baseRequest.setHandled(true);
	}

}
