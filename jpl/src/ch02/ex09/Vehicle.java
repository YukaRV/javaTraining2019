package ch02.ex09;

public class Vehicle {
	private double speed,degree;
	private String owner = "unknown";

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

	public static void main(String[] args) {
		Vehicle car1 = new Vehicle("Taro");
		car1.setSpeed(100);
		System.out.println(car1.getId() + ": "+car1.getOwner() + "'s car");
		System.out.println(car1.getSpeed()+" (m/s), "+car1.getDegree() + " (degree)");
		System.out.println("max: "+Vehicle.getMaxId());
		System.out.println();

		Vehicle bike1 = new Vehicle();
		car1.setDegree(45);
		System.out.println(bike1.getId() + ": "+bike1.getOwner() + "'s car");
		System.out.println(bike1.getSpeed()+" (m/s), "+bike1.getDegree() + " (degree)");
		System.out.println("max: "+Vehicle.getMaxId());
		System.out.println();
	}
}