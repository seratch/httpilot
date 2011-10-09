package server.handler;

import com.github.seratch.httpilot.request.Method;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

public class PutMethodHandler extends MethodHandler {

	@Override
	public Method getMethod() {
		return Method.PUT;
	}

	@SuppressWarnings("unchecked")
	public void _handle(Boolean isAllowed, Method method, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("---(HTTP Headers)--------");
		Enumeration<String> iter = request.getHeaderNames();
		while (iter.hasMoreElements()) {
			String headerName = iter.nextElement();
			System.out.println(headerName + "->"
					+ request.getHeader(headerName));
		}
		System.out.println("-------------------------");
		if (isAllowed) {
			InputStream is = request.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = r.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			System.out.println("-------------------------");
			if (sb.length() == 0) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				response.setStatus(HttpServletResponse.SC_CREATED);
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
