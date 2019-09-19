package jpl.test.ch02.ex01;
import static org.junit.Assert.*;

import org.junit.Test;

import jpl.src.ch02.ex01.Vehicle;

public class VehicleTest {
   @Test
   public void initTest() {
	   int speed = 100;
	   int degree = 80;
	   int degree2 = -80;
	   String owner = "TestTaro";
		Vehicle car1 = new Vehicle();
		car1.setSpeed(speed);
		assertEquals(speed,(int)(car1.getSpeed()));

		car1.setDegree(degree);
		assertEquals(degree,(int)(car1.getDegree()));

		car1.setDegree(degree2);
		assertEquals((360+degree2),(int)(car1.getDegree()));

		car1.setOwner(owner);
		assertEquals(owner,car1.getOwner());
	}
}

