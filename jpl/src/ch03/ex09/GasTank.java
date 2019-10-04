package ch03.ex09;

class GasTank extends EnergySource{
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