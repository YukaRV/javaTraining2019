package jpl.test.ch02.ex17;
import static org.junit.Assert.*;

import org.junit.Test;

import jpl.src.ch02.ex17.Vehicle;

public class VehicleTest {

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
}

