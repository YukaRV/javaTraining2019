package ch03.ex06;

class Battery extends EnergySource{
	private final double BATTERY_CAPACITY;
	private double battery;
	public Battery(double capacity) {
		BATTERY_CAPACITY = capacity;
	}
	public boolean empty() {
		if (battery > 0) return false;
		return true;
	}
}