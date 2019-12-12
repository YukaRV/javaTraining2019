package ch13.ex01;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


public class CountCharTest {
	@Test
	public void countTest() {
		String str = "abcdefghijktesteoepe";
		int expected = 5;
		int actual = CountChar.countChar(str, 'e');
		assertEquals(expected, actual);

		str = "abcdfghij";
		expected = 0;
		actual = CountChar.countChar(str, 'e');
		assertEquals(expected, actual);

		str += "e";
		expected = 1;
		actual = CountChar.countChar(str, 'e');
		assertEquals(expected, actual);
	}
}

