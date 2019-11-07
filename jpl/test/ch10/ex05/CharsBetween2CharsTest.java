package ch10.ex05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CharsBetween2CharsTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void helloWorldTest() {
		char c0 = 'a',c1='z';
		String expectChars = "bcdefghijklmnopqrstuvwxy";
		CharsBetween2Chars.printCharsBetween2Chars(c0,c1);
		String expect = "("+c0+","+c1+") = "+expectChars;
		String result = outContent.toString();
		assertEquals(expect, result);
   }
}
