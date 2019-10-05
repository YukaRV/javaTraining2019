package ch03.ex01;

// 第2章の練習問題のVehicleクラスを拡張してPassengerVehicleクラスを作成し、
// 車が持っている座席数と現在座っている人数を返す機能を追加しなさい。
// PassengerVehicleに新たなmainメソッドを定義して、
// PassengerVehicleオブジェクトを数個生成して表示するようにしなさい。

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
		PassengerVehicle pv = new PassengerVehicle(4);
		PassengerVehicle pv2 = new PassengerVehicle(6);
		PassengerVehicle pv3 = new PassengerVehicle(20);
		System.out.println(pv.getNumOfSitting());
		pv.setNumOfSitting(4);
		System.out.println(pv.getNumOfSitting());
//		pv.setNumOfSitting(20);
	}
}
