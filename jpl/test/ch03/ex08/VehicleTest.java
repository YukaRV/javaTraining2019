package ch03.ex08;
import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {
	@Test
	public void cloneTest() {
		PassengerVehicle pv = new PassengerVehicle("Taro",50,45,20);
		pv.setNumOfSitting(4);
		PassengerVehicle pv2 = pv.clone();
		assertNotEquals(pv, pv2);
		assertEquals(pv.getNumOfSeat(), pv2.getNumOfSeat());
		assertEquals(pv.getNumOfSitting(), pv2.getNumOfSitting());
	}
}

