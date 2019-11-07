package ch10.ex01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class EscapeSequenceTest {

	@Test
	public void escapeTest() {
		String txt = "test\n\\'\"\106";
		String expected = "test\\n\\\\\'\"F";
		String actual = EscapeSequence.encodeEscSeq(txt);
		assertEquals(expected, actual);
   }

}
