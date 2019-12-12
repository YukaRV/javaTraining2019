package ch13.ex05;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;


public class SeparatorTest {
	@Test
	public void separateTest() {
		String numStr = "1234567890";
		String expect = "1,234,567,890";
		String actual = Separator.digitSeparator(numStr);
		assertThat(actual, is(expect));

		numStr = "12345678901";
		expect = "12,345,678,901";
		actual = Separator.digitSeparator(numStr);
		assertThat(actual, is(expect));

		numStr = "123456789012";
		expect = "123,456,789,012";
		actual = Separator.digitSeparator(numStr);
		assertThat(actual, is(expect));
	}
}

