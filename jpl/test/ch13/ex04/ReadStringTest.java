package ch13.ex04;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;

import org.junit.Test;


public class ReadStringTest {
	@Test
	public void readTest() {
		String lines = "Boolean true\n"
						+ "Byte 1\n"
						+ "Short 2\n"
						+ "Integer 3\n"
						+ "Long 4\n"
						+ "Float 5\n"
						+ "Double 6";
		ArrayList<Object> result = ReadString.readTnV(lines);
		assertThat(result.get(0), is(true));
		assertThat(result.get(0), instanceOf(Boolean.class));
		assertThat(result.get(1), is((byte)1));
		assertThat(result.get(1), instanceOf(Byte.class));
		assertThat(result.get(2), is((short)2));
		assertThat(result.get(2), instanceOf(Short.class));
		assertThat(result.get(3), is(3));
		assertThat(result.get(3), instanceOf(Integer.class));
		assertThat(result.get(4), is((long)4));
		assertThat(result.get(4), instanceOf(Long.class));
		assertThat(result.get(5), is((float)5.0));
		assertThat(result.get(5), instanceOf(Float.class));
		assertThat(result.get(6), is(6.0));
		assertThat(result.get(6), instanceOf(Double.class));
	}
}

