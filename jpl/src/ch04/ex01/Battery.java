package ch04.ex01;

class Battery implements EnergySource{
	private final double BATTERY_CAPACITY;
	private double battery;
	public Battery(double capacity) {
		BATTERY_CAPACITY = capacity;
		battery = capacity;
	}
	public boolean empty() {
		if (battery > 0) return false;
		return true;
	}
}