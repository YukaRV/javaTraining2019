package ch07.ex01;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldTest {
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
	   String args[] = {};
		HelloWorld.main(args);
		String expect = "Hello, World\r\n";
		String result = outContent.toString();
		int d = diff(expect,result);
		assertEquals(expect, result);
   }
	public int diff(String expect,String target) {
		for (int i = 0;i < target.length();i++) {
			int e = expect.length();
			int t = target.length();
			if (i == expect.length() || expect.charAt(i) != target.charAt(i)) {
				char d = expect.charAt(i);
				return i;
			}
		}
		if (expect.length() != target.length()) return target.length();
		return -1;
	}
}
