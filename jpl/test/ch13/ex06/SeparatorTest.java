package ch13.ex06;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;


public class SeparatorTest {
	@Test
	public void separateTest() {
		String numStr = "1234567890";
		String expect = "12:3456:7890";
		String actual = Separator.digitSeparator(numStr, ':', 4);
		assertThat(actual, is(expect));

		numStr = "12345678901";
		expect = "123:4567:8901";
		actual = Separator.digitSeparator(numStr, ':', 4);
		assertThat(actual, is(expect));

		numStr = "123456789012";
		expect = "1234:5678:9012";
		actual = Separator.digitSeparator(numStr, ':', 4);
		assertThat(actual, is(expect));

		numStr = "1234567890123";
		expect = "1:2345:6789:0123";
		actual = Separator.digitSeparator(numStr, ':', 4);
		assertThat(actual, is(expect));
	}
}

