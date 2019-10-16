package ch06.ex02;
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

   @Test
   public void rotateTest() {
		Vehicle car1 = new Vehicle("Taro");
		int[] turnDeg = {0,100,-10};
		int ansDeg = 0;
		for (int i = 0;i < turnDeg.length;i++) {
			ansDeg += turnDeg[i];
			car1.turn(turnDeg[i]);
	      assertEquals("wrong: "+i, ansDeg, Math.round(car1.getDegree()));
		}
   }

   @Test
   public void rotateLRTest() {
		Vehicle car1 = new Vehicle("Taro");
		car1.turn(Vehicle.TURN.TURN_LEFT);
		String except = Vehicle.TURN.TURN_LEFT+"\r\n";
		String actual = outContent.toString();
		assertEquals(except, actual);
		car1.turn(Vehicle.TURN.TURN_RIGHT);
		except += Vehicle.TURN.TURN_RIGHT+"\r\n";
		actual = outContent.toString();
		assertEquals(except, actual);
   }
}

