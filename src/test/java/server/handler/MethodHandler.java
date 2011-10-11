package server.handler;

import httpilot.Method;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MethodHandler extends AbstractHandler {

	public abstract Method getMethod();

	@Override
	public void handle(String target, Request baseRequest,
	                   HttpServletRequest request, HttpServletResponse response) {
		try {
			_handle(request.getMethod().equals(getMethod().toString()),
					getMethod(), baseRequest, request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void _handle(Boolean isAllowed, Method method,
	                    Request baseRequest, HttpServletRequest request,
	                    HttpServletResponse response) throws Exception {
		if (isAllowed) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			String toReturn = request.getParameter("toReturn");
			if (toReturn != null) {
				response.getWriter().print(toReturn);
			} else {
				response.getWriter().print("おｋ");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.getWriter().print("だｍ");
		}
		baseRequest.setHandled(true);
	}

}