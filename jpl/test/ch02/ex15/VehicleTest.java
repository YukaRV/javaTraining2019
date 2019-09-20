package ch02.ex15;
import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {
   @Test
   public void speedTest() {
	   int speed = 100;
		Vehicle car1 = new Vehicle("Taro");
		car1.changeSpeed(speed);
		assertEquals(speed,(int)(car1.getSpeed()));

		car1.stop();
		assertEquals(0,(int)(car1.getSpeed()));
	}
}

