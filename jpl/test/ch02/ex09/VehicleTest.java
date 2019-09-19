package jpl.test.ch02.ex09;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import jpl.src.ch02.ex09.Vehicle;

public class VehicleTest {
   @Test
   public void idTest() {
	   int length = 52;
	   Vehicle[] vs = new Vehicle[length];
	   for (int i = 0;i < length;i++) {
		   vs[i] = new Vehicle();
		   assertEquals(i, Vehicle.getMaxId());
	   }
	}
}

