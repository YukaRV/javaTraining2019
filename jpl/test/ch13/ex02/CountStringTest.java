package ch13.ex02;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


public class CountStringTest {
	@Test
	public void countTest() {
		String str = "testatestbctdestfgtesthijktesteotestepe";
		int expected = 5;
		int actual = CountString.countString(str, "test");
		assertEquals(expected, actual);

		str = "abcdfghij";
		expected = 0;
		actual = CountString.countString(str, "test");
		assertEquals(expected, actual);

		str += "test";
		expected = 1;
		actual = CountString.countString(str, "test");
		assertEquals(expected, actual);

		str += "testest";
		expected = 3;
		actual = CountString.countString(str, "test");
		assertEquals(expected, actual);

	}
}

