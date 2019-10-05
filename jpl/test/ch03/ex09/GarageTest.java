package ch03.ex09;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GarageTest {
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
		Garage.main(args);
		String resultStr = outContent.toString();
		String expectStr = "0: Alice's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)" + brRN +
				"3: Alice's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)" + brRN +
				"1: Bob's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)" + brRN +
				"4: Bob's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)" + brRN +
				"2: Charley's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)" + brRN +
				"5: Charley's vehicle" + brN +
				"25.0 (m/s), 0.0 (degree)"+brRN;
		assertEquals(expectStr, resultStr);
	}

	// mainTestよりアルファベット後の名前にしないとstatic要素が誤りになる
	@Test
	public void vehicleCloneTest() {
		Vehicle vehicles[] = {
				new Vehicle("Alice", 25, 0),
				new Vehicle("Bob", 25, 0),
				new Vehicle("Charley", 25, 0)
		};
		Garage g = new Garage(vehicles);
		Garage g2 = g.clone();
		Vehicle v1,v2;
		for (int i = 0;i < vehicles.length;i++) {
			v1 = g.getVehicles()[i];
			v2 = g2.getVehicles()[i];
			assertNotEquals(v1, v2);
			assertNotEquals(v1.getId(), v2.getId());
			assertEquals(v1.getOwner(), v2.getOwner());
		}
	}
}

