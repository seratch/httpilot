package server.handler;

import httpilot.Method;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

public class OptionsMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.OPTIONS;
	}

	@SuppressWarnings("unchecked")
	public void _handle(Boolean isAllowed, Method method,
			Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("---(HTTP Headers)--------");
		Enumeration<String> iter = request.getHeaderNames();
		while (iter.hasMoreElements()) {
			String headerName = iter.nextElement();
			System.out.println(headerName + "->"
					+ request.getHeader(headerName));
		}
		System.out.println("-------------------------");
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
