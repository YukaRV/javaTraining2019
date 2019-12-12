package ch13.ex03;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;


public class DelimitedStringTest {
	@Test
	public void delimitedTest() {
		// 開始文字が見つからない
		String str = "abcdefgabcdef";
		char start = 'x';
		char end = 'f';
		String[] expected = {};
		String[] actual = DelimitedString.delimitedStrings(str, start, end);
		assertArrayEquals(expected, actual);

		// 終了文字が見つからない
		start = 'c';
		end = 'j';
		String[] expected2 = {"cdefgabcdef","cdef"};
		actual = DelimitedString.delimitedStrings(str, start, end);
		assertArrayEquals(expected2, actual);

		// 開始文字が終了文字の後にある
		start = 'c';
		end = 'f';
		String[] expected3 = {"cdef","cdefgabcdef","cdef"};
		actual = DelimitedString.delimitedStrings(str, start, end);
		assertArrayEquals(expected3, actual);

		// 開始文字と終了文字が見つかった
		str = "abcdefg";
		start = 'f';
		end = 'c';
		String[] expected4 = {};
		actual = DelimitedString.delimitedStrings(str, start, end);
		assertArrayEquals(expected4, actual);

	}
}

