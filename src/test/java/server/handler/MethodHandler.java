package server.handler;

import com.github.seratch.httpilot.request.Method;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.getWriter().print("おｋ");
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.getWriter().print("だｍ");
		}
		baseRequest.setHandled(true);
	}

}