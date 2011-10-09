package com.github.seratch.httpilot;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import com.github.seratch.httpilot.request.FormData;
import com.github.seratch.httpilot.request.FormData.FileInput;
import com.github.seratch.httpilot.request.FormData.TextInput;
import com.github.seratch.httpilot.request.Method;
import com.github.seratch.httpilot.request.Request;
import com.github.seratch.httpilot.response.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import server.HttpServer;
import server.PostFormdataServer;
import server.handler.DeleteMethodHandler;
import server.handler.GetMethodHandler;
import server.handler.HeadMethodHandler;
import server.handler.OptionsMethodHandler;
import server.handler.PostMethodHandler;
import server.handler.PutMethodHandler;
import server.handler.TraceMethodHandler;

public class HTTPTest {

	Runnable getRunnable(HttpServer server) {
		final HttpServer _server = server;
		return new Runnable() {
			@Override
			public void run() {
				try {
					_server.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Test
	public void type() throws Exception {
		assertThat(HTTP.class, notNullValue());
	}

	@Test
	public void get_A$Request_withTestServer() throws Exception {
		final HttpServer server = new HttpServer(new GetMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			Response response = HTTP.get(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getHeaders().size(), is(greaterThan(0)));
			assertThat(response.getTextBody(), is("おｋ"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void get_A$Request_text() throws Exception {
		Request request = new Request("http://seratch.net/");
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(200));
		assertThat(response.getTextBody().length(), is(greaterThan(0)));
	}

	@Test
	public void get_A$Request_text_charset() throws Exception {
		Request request = new Request("http://seratch.net/", "EUC-JP");
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(200));
		assertThat(response.getTextBody().length(), is(greaterThan(0)));
	}

	@Test
	public void get_A$Request_text_404() throws Exception {
		Request request = new Request("http://seratch.net/sss");
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(404));
		assertThat(response.getTextBody().length(), is(greaterThan(0)));
	}

	@Test(expected = HTTPIOException.class)
	public void get_A$Request_text_404_exception() throws Exception {
		Request request = new Request("http://seratch.net/sss");
		request.setEnableThrowingIOException(true);
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(404));
		assertThat(response.getTextBody().length(), is(greaterThan(0)));
	}

	@Test
	public void get_A$Request_text_via_SSL() throws Exception {
		Request request = new Request("https://github.com/seratch");
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(200));
		assertThat(response.getTextBody().length(), is(greaterThan(0)));
	}

	@Test
	public void tmp() throws Exception {
		Request request = new Request("http://seratch.net/");
		Response response = HTTP.trace(request);
		assertThat(response.getStatus(), is(200));
		System.out.println(response.getTextBody());
	}

	@Test
	public void get_A$Request_jpg() throws Exception {
		Request request = new Request("http://seratch.net/images/self_face.jpg");
		Response response = HTTP.get(request);
		assertThat(response.getStatus(), is(200));
		FileOutputStream os = new FileOutputStream("target/self_face.jpg");
		os.write(response.getBody());
		os.close();
	}

	@Test(expected = MalformedURLException.class)
	public void get_A$Request_protocol_error() throws Exception {
		Request request = new Request("ttp://seratch.net/");
		HTTP.get(request);
	}

	@Test
	public void post_A$Request_ext() throws Exception {
		Map<String, Object> formParams = new HashMap<String, Object>();
		formParams.put("name", "Andy");
		Request request = new Request("http://seratch.net/", formParams);
		Response response = HTTP.post(request);
		assertThat(response.getStatus(), is(200));
	}

	@Test
	public void post_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new PostMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Map<String, Object> formParams = new HashMap<String, Object>();
			formParams.put("userName", "日本語");
			Request request = new Request("http://localhost:8888/", formParams);
			Response response = HTTP.post(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is("userName:日本語"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void post_A$Request_multipart() throws Exception {
		final HttpServer server = new PostFormdataServer();
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			List<FormData> formDataList = new ArrayList<FormData>();
			FormData entry1 = new FormData("toResponse", new TextInput("日本語",
					"UTF-8"));
			entry1.setContentType("text/plain");
			formDataList.add(entry1);
			FormData entry2 = new FormData("formData2", new TextInput("2222",
					"UTF-8"));
			formDataList.add(entry2);
			request.setMultipartFormData(formDataList);
			Response response = HTTP.post(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is("日本語"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void post_A$Request_file() throws Exception {
		final HttpServer server = new PostFormdataServer();
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			// Request request = new Request("http://localhost:8888/");
			Request request = new Request("http://seratch.net/");
			List<FormData> multipart = new ArrayList<FormData>();
			FormData entry1 = new FormData("toResponse", new TextInput("日本語",
					"UTF-8"));
			entry1.setTextBody("日本語", "UTF-8");
			multipart.add(entry1);
			FormData entry2 = new FormData("readme", new FileInput(new File(
					"readme.md"), "text/plain"));
			multipart.add(entry2);
			request.setMultipartFormData(multipart);
			Response response = HTTP.post(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is("日本語"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void put_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new PutMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			// Request request = new Request("http://localhost:8888/");
			Request request = new Request("http://seratch.net/");
			// Response getResponse = HTTP.get(request);
			// assertThat(getResponse.getStatus(), is(405));
			// assertThat(getResponse.getTextBody(), is("だｍ"));
			request.setBody(
					"<user><id>1234</id><name>Andy</name></user>".getBytes(),
					"text/xml");
			Response response = HTTP.put(request);
			assertThat(response.getStatus(), is(201));
			assertThat(response.getTextBody(), is(""));
		} finally {
			server.stop();
		}
	}

	@Test
	public void delete_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new DeleteMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			Response getResponse = HTTP.get(request);
			assertThat(getResponse.getStatus(), is(405));
			assertThat(getResponse.getTextBody(), is("だｍ"));
			Response response = HTTP.delete(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is("おｋ"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void head_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new HeadMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			Response getResponse = HTTP.get(request);
			assertThat(getResponse.getStatus(), is(405));
			assertThat(getResponse.getTextBody(), is("だｍ"));
			Response response = HTTP.head(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is(""));
		} finally {
			server.stop();
		}
	}

	@Test
	public void options_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new OptionsMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			Response getResponse = HTTP.get(request);
			assertThat(getResponse.getStatus(), is(405));
			assertThat(getResponse.getTextBody(), is("だｍ"));
			Response response = HTTP.options(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getHeaders().get("Allow").toString(),
					is("[GET, HEAD, OPTIONS, TRACE]"));
			assertThat(response.getTextBody(), is("おｋ"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void trace_A$Request() throws Exception {
		final HttpServer server = new HttpServer(new TraceMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();

			Request request = new Request("http://localhost:8888/");
			Response getResponse = HTTP.get(request);
			assertThat(getResponse.getStatus(), is(405));
			assertThat(getResponse.getTextBody(), is("だｍ"));
			Response response = HTTP.trace(request);
			assertThat(response.getStatus(), is(200));
			assertThat(response.getTextBody(), is("おｋ"));
		} finally {
			server.stop();
		}
	}

	@Test
	public void request_A$HttpMethod$Request() throws Exception {
		Method method = Method.GET;
		Request request = new Request("http://seratch.net/");
		Response response = HTTP.request(method, request);
		assertThat(response.getStatus(), is(200));
	}

}
