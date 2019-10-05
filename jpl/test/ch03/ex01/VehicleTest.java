package ch03.ex01;
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

	String br = "\r\n";
	// mainのテスト
	@Test
	public void mainTest() {
		String[] args = {};
		PassengerVehicle.main(args);
		assertEquals("0"+br+"4"+br, outContent.toString());
	}

	// 1. numOfSeat
	// 値がおかしくないか
	@Test(expected = IllegalArgumentException.class)
	public void numOfSeatTest() {
		String owner = "Taro";
		int numOfSeat = 0;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		PassengerVehicle pv2 = new PassengerVehicle(owner,numOfSeat);
		PassengerVehicle pv3 = new PassengerVehicle(owner,0,0,numOfSeat);
		assertEquals(numOfSeat,pv1.getNumOfSeat());
		assertEquals(numOfSeat,pv2.getNumOfSeat());
		assertEquals(numOfSeat,pv3.getNumOfSeat());
		numOfSeat = 1;
		pv1 = new PassengerVehicle(numOfSeat);
		pv2 = new PassengerVehicle(owner,numOfSeat);
		pv3 = new PassengerVehicle(owner,0,0,numOfSeat);
		assertEquals(numOfSeat,pv1.getNumOfSeat());
		assertEquals(numOfSeat,pv2.getNumOfSeat());
		assertEquals(numOfSeat,pv3.getNumOfSeat());
	}

	// 2. numOfSitting
	// 値がおかしくないか
	@Test
	public void underSeatPassengerTest() {
		int numOfSeat = 3;
		int numOfSitting = 2;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.setNumOfSitting(numOfSitting);
		assertEquals(numOfSitting,pv1.getNumOfSitting());
	}

	@Test(expected = IllegalArgumentException.class)
	public void overSeatPassengerTest() {
		int numOfSeat = 3;
		int numOfSitting = 5;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.setNumOfSitting(numOfSitting);

	}
	@Test(expected = IllegalArgumentException.class)
	public void minusPassengerTest() {
		// チェック事項
		// 座席より多くないか
		int numOfSeat = 3;
		int numOfSitting = -1;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.setNumOfSitting(numOfSitting);
	}
}

