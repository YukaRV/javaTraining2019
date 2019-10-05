package ch03.ex11;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortTest {
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

	String brRN = "\r\n";
	String brN = "\n";
	// mainのテスト
	@Test
	public void mainTest() {
		String[] args = {};
		TestSort.main(args);
		String resultStr = outContent.toString();
		String expectStr = "this sort is already in use." + brRN +
				"Metrics: 0 probes 6 compares 2 swaps" + brRN +
				"	0.013" + brRN +
				"	0.3" + brRN +
				"	3.17" + brRN +
				"	7.9" + brRN +
				"";
		assertEquals(expectStr, resultStr);
	}
}

