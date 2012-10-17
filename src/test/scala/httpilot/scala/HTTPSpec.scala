package httpilot.scala

import org.specs2.mutable.Specification
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{ Request => BaseRequest }
import javax.servlet.http._

class HTTPSpec extends Specification {

  "HTTP" should {

    "get" in {
      val response = HTTP.get("https://github.com/seratch/httpilot")
      response.status must equalTo(200)
      response.asString.length must be_>(0)
    }

    "get with charset" in {
      val response = HTTP.get("https://github.com/seratch/httpilot", "UTF-8")
      response.status must equalTo(200)
      response.asString.length must be_>(0)
    }

    "post with data string" in {
      val server = new org.eclipse.jetty.server.Server(8887)
      try {
        server.setHandler(postHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.post("http://localhost:8887/", "foo=bar")
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
      }
    }

    "post with Map" in {
      val server = new org.eclipse.jetty.server.Server(8897)
      try {
        server.setHandler(postHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.post("http://localhost:8897/", Map("foo" -> "bar"))
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
      }
    }

    "put with data string" in {
      val server = new org.eclipse.jetty.server.Server(8886)
      try {
        server.setHandler(putHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.put("http://localhost:8886/", "foo=bar")
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
      }
    }

    "put with data Map" in {
      val server = new org.eclipse.jetty.server.Server(8896)
      try {
        server.setHandler(putHandler)
        new Thread(runnable(server)).start()
        Thread.sleep(300L)

        val response = HTTP.put("http://localhost:8896/", Map("foo" -> "bar"))
        response.status must equalTo(200)
        response.asString must equalTo("foo:bar")
      } finally {
        server.stop
      }
    }

  }

  def runnable(server: Server) = {
    val _server = server
    new Runnable() {
      def run() {
        try {
          _server.start();
        } catch {
          case e => e.printStackTrace
        }
      }
    }
  }

  val postHandler = new AbstractHandler {
    def handle(target: String, baseReq: BaseRequest, req: HttpServletRequest, resp: HttpServletResponse) = {
      try {
        if (req.getMethod().equals("POST")) {
          val foo = req.getParameter("foo")
          val result = "foo:" + foo
          resp.setCharacterEncoding("UTF-8")
          resp.getWriter().print(result)
          baseReq.setHandled(true)
          resp.setStatus(HttpServletResponse.SC_OK)
        } else {
          resp.setStatus(HttpServletResponse.SC_FORBIDDEN)
        }
      } catch { case e => e.printStackTrace }
    }
  }

  val putHandler = new AbstractHandler {
    def handle(target: String, baseReq: BaseRequest, req: HttpServletRequest, resp: HttpServletResponse) = {
      try {
        if (req.getMethod().equals("PUT")) {
          val foo = req.getParameter("foo")
          val result = "foo:" + foo
          resp.setCharacterEncoding("UTF-8")
          resp.getWriter().print(result)
          baseReq.setHandled(true)
          resp.setStatus(HttpServletResponse.SC_OK)
        } else {
          resp.setStatus(HttpServletResponse.SC_FORBIDDEN)
        }
      } catch { case e => e.printStackTrace }
    }
  }

}
