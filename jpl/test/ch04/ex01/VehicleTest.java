package ch04.ex01;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VehicleTest {
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
	// mainのテスト
	@Test
	public void mainTest() {
		PassengerVehicle pv = new PassengerVehicle(20);
		pv.setNumOfSitting(5);
		pv.start();
		String resultStr = outContent.toString();
		String expectStr = "started."+brRN;
		assertEquals(expectStr, resultStr);
	}

	@Test
	public void gasTankTest() {
		EnergySource es = new GasTank(100);
		assertEquals(false, es.empty());
		es = new GasTank(0);
		assertEquals(true, es.empty());
	}

	@Test
	public void batteryTest() {
		EnergySource es = new Battery(100);
		assertEquals(false, es.empty());
		es = new Battery(0);
		assertEquals(true, es.empty());
	}
}

