package ch03.ex09;

class PassengerVehicle extends Vehicle implements Cloneable{
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

	@Override
	public PassengerVehicle clone(){
		PassengerVehicle cloneVehicle;
		cloneVehicle = (PassengerVehicle)(super.clone());
		return cloneVehicle;
	}
	public static void main(String[] args) {
		PassengerVehicle pv = new PassengerVehicle("Taro",50,45,20);
		pv.setNumOfSitting(4);
		PassengerVehicle pv2 = pv.clone();
		System.out.println(pv);
		System.out.println(pv2);
		System.out.println(pv.getNumOfSeat());
		System.out.println(pv2.getNumOfSeat());
		System.out.println(pv.getNumOfSitting());
		System.out.println(pv2.getNumOfSitting());
	}
}
