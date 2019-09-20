package ch02.ex17;

public class Vehicle {
	private double speed,degree;
	private String owner = "unknown";
	public static final String TURN_LEFT = "TURN_LEFT";
	public static final String TURN_RIGHT = "TURN_RIGHT";

	private static int singleIdNext;
	private int singleId;
	public Vehicle() {
		singleId = singleIdNext++;
	}
	public Vehicle(String owner) {
		this();
		this.owner = owner;
	}

	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double s) {
		speed = s;
	}
	public void changeSpeed(double s) {
		setSpeed(s);
	}
	public void stop() {
		setSpeed(0);
	}

	public double getDegree() {
		return degree;
	}
	public void setDegree(double d) {
		int maxDeg = 360;
		if (d >= 0)
			degree = d%maxDeg;
		else
			degree = maxDeg-(-d%maxDeg);// TODO test
	}
	public void turn(double d) {
		setDegree(degree+d);
	}
	public void turn(String d) {
		if (d == Vehicle.TURN_LEFT)
			System.out.println("TURN LEFT");
		else if (d == Vehicle.TURN_RIGHT)
			System.out.println("TURN RIGHT");
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String o) {
		owner = o;
	}

	public int getId() {
		return singleId;
	}
	public static int getMaxId() {
		return (singleIdNext-1);
	}

	@Override
	public String toString() {
		String txt = getId() + ": "+getOwner() + "'s vehicle";
		txt += "\n";
		txt += getSpeed()+" (m/s), "+getDegree() + " (degree)";
		return txt;
	}

	public static void main(String[] args) {
		Vehicle car1 = new Vehicle("Taro");
		car1.changeSpeed(100);
		car1.turn(10);
		System.out.println(car1);
		car1.turn(Vehicle.TURN_LEFT);
		System.out.println(car1);
	}
}