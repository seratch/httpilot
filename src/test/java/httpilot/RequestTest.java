package httpilot;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class RequestTest {

	@Test
	public void type() throws Exception {
		assertThat(Request.class, notNullValue());
	}

	@Test
	public void instantiation() throws Exception {
		Request target = new Request("https://github.com/seratch");
		assertThat(target, notNullValue());
	}

	@Test
	public void getHeader_A$String() throws Exception {
		Request target = new Request("https://github.com/seratch");
		String name = "Connection";
		String actual = target.getHeader(name);
		String expected = "keep-alive";
		assertThat(actual, is(equalTo(expected)));
	}

}
