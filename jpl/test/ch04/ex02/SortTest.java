package ch04.ex02;
import static org.junit.jupiter.api.Assertions.*;

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

	static Double[] testData = {
			0.3, 1.3e-2, 7.9, 3.17
	};
	static Double[] sortData = {
			1.3e-2, 0.3, 3.17, 7.9
	};
	static String[] testData2 = {
			"Charely", "Bob", "David", "Alice"
	};
	static String[] sortData2 = {
			"Alice", "Bob", "Charely", "David"
	};

	String brRN = "\r\n";
	String brN = "\n";
	@Test
	public void doubleTest() {
		SortHarness<Double> bsort = new SimpleSortDoubleR();
		SortMetrics metrics = bsort.sort(testData);
		System.out.println("Metrics: " + metrics);
		String resultStr = outContent.toString();
		String expectStr = "Metrics: 0 probes 6 compares 2 swaps";
		assertArrayEquals(sortData, testData);
	}
	@Test
	public void stringTest() {
		SortHarness<String> bsort = new SimpleSortString();
		SortMetrics metrics = bsort.sort(testData2);
		System.out.println("Metrics: " + metrics);
		String resultStr = outContent.toString();
		String expectStr = "Metrics: 0 probes 6 compares 4 swaps";
		assertArrayEquals(sortData2, testData2);
	}
}

