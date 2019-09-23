package ch03.ex01;
import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {
	//TODO: ch03.ex01用に書き換え
	// チェック事項
	// 1. numOfSeat
	// 値がおかしくないか: 0,1
	// 2. numOfSitting
	// 値がおかしくないか: 0,1
	// 座席より多くないか

	@Test(expected = IllegalArgumentException.class)
	public void zeroSeatTest() {
		int numOfSeat = 0;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.getNumOfSeat();

	}
	@Test
	public void oneSeatTest() {
		String owner = "Taro";
		int numOfSeat = 1;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		PassengerVehicle pv2 = new PassengerVehicle(owner,numOfSeat);
		PassengerVehicle pv3 = new PassengerVehicle(owner,0,0,numOfSeat);
		assertEquals(numOfSeat,pv1.getNumOfSeat());
		assertEquals(numOfSeat,pv2.getNumOfSeat());
		assertEquals(numOfSeat,pv3.getNumOfSeat());
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
		int numOfSeat = 3;
		int numOfSitting = -1;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.setNumOfSitting(numOfSitting);
	}
	@Test
	public void underSeatPassengerTest() {
		int numOfSeat = 3;
		int numOfSitting = 2;
		PassengerVehicle pv1 = new PassengerVehicle(numOfSeat);
		pv1.setNumOfSitting(numOfSitting);
		assertEquals(numOfSitting,pv1.getNumOfSitting());
	}
}

