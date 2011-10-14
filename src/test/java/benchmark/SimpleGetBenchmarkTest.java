package benchmark;

import httpilot.HTTP;
import httpilot.Method;
import httpilot.Request;
import httpilot.Response;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;
import server.HttpServer;
import server.handler.GetMethodHandler;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SimpleGetBenchmarkTest {

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
	public void createInstanceEveryTime() throws Exception {
		final HttpServer server = new HttpServer(new GetMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();
			Thread.sleep(300L);

			String URL = "http://localhost:8888/";
			int timesToCall = 300;

			{
				long startTime = System.currentTimeMillis();
				for (int i = 0; i < timesToCall; i++) {
					Request request = new Request(URL);
					HTTP.get(request);
					Thread.sleep(10L);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("HTTPilot 300 GET req : " + (endTime - startTime - 10 * timesToCall) + " milliseconds");
				Thread.sleep(300L);
			}

			{
				long startTime = System.currentTimeMillis();
				for (int i = 0; i < timesToCall; i++) {
					HttpClient client = new HttpClient();
					HttpMethod method = new GetMethod(URL);
					client.executeMethod(method);
					Thread.sleep(10L);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Commons HttpClient 300 GET req : " + (endTime - startTime - 10 * timesToCall) + " milliseconds");
				Thread.sleep(300L);
			}

			{
				long startTime = System.currentTimeMillis();
				for (int i = 0; i < timesToCall; i++) {
					HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
					HttpMethod method = new GetMethod(URL);
					client.executeMethod(method);
					Thread.sleep(10L);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Commons HttpClient(MultiThreaded) 300 GET req : " + (endTime - startTime - 10 * timesToCall) + " milliseconds");
				Thread.sleep(300L);
			}

		} finally {
			server.stop();
			Thread.sleep(300L);
		}

	}

	@Test
	public void reuseInstance() throws Exception {
		final HttpServer server = new HttpServer(new GetMethodHandler());
		try {
			Runnable runnable = getRunnable(server);
			new Thread(runnable).start();
			Thread.sleep(300L);

			String URL = "http://localhost:8888/";
			int timesToCall = 300;

			{
				Request request = new Request(URL);
				long startTime = System.currentTimeMillis();
				for (int i = 0; i < timesToCall; i++) {
					HTTP.get(request);
					Thread.sleep(10L);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("HTTPilot 300 GET req : " + (endTime - startTime - 10 * timesToCall) + " milliseconds");
				Thread.sleep(300L);
			}

			{
				HttpClient client = new HttpClient();
				long startTime = System.currentTimeMillis();
				HttpMethod method = new GetMethod(URL);
				for (int i = 0; i < timesToCall; i++) {
					client.executeMethod(method);
					Thread.sleep(10L);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Commons HttpClient 300 GET req : " + (endTime - startTime - 10 * timesToCall) + " milliseconds");
				Thread.sleep(300L);
			}

		} finally {
			server.stop();
			Thread.sleep(100L);
		}
	}

}
