package ch04.ex01;

class GasTank implements EnergySource{
	private final double GAS_CAPACITY;
	private double gas;
	public GasTank(double capacity) {
		GAS_CAPACITY = capacity;
		gas = capacity;
	}
	public boolean empty() {
		if (gas > 0) return false;
		return true;
	}
}