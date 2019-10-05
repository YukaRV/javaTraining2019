package ch03.ex06;

class PassengerVehicle extends Vehicle{
	private final int numOfSeat;
	private int numOfSitting;
	public PassengerVehicle(int numOfSeat) {
		super();
		if (isValidNumOfSeat(numOfSeat))
			this.numOfSeat = numOfSeat;
		else
			throw new IllegalArgumentException();
	}
	public PassengerVehicle(String owner, int numOfSeat) {
		super(owner);
		if (isValidNumOfSeat(numOfSeat))
			this.numOfSeat = numOfSeat;
		else
			throw new IllegalArgumentException();
	}
	public PassengerVehicle(String owner, double speed, double degree, int numOfSeat) {
		super(owner,speed,degree);
		if (isValidNumOfSeat(numOfSeat))
			this.numOfSeat = numOfSeat;
		else
			throw new IllegalArgumentException();
	}

	public final int getNumOfSeat() {
		return numOfSeat;
	}

	public final int getNumOfSitting() {
		return numOfSitting;
	}
	public void setNumOfSitting(int numOfSitting) {
		if (isValidNumOfSitting(numOfSitting))
			this.numOfSitting = numOfSitting;
		else
			throw new IllegalArgumentException("numOfSittingは不正な値です: "+numOfSitting);
	}


	public boolean isValidNumOfSeat(int numOfSeat) {
		if (numOfSeat <= 0) return false;
		return true;
	}
	public boolean isValidNumOfSitting(int numOfSitting) {
		if (numOfSitting <= 0) return false;
		if (numOfSitting > numOfSeat) return false;
		return true;
	}
	public static void main(String[] args) {
		PassengerVehicle pv = new PassengerVehicle(20);
		pv.setNumOfSitting(5);
		pv.start();
	}
}
