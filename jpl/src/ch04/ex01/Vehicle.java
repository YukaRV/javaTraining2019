package ch04.ex01;

// 3.1
// 86頁の練習問題3.6の解答を、抽象クラスではなく、
// EnergySourceのためのインタフェースを使用して書き直しなさい。

class Vehicle {
	private double speed,degree;
	private String owner;
	public static final String TURN_LEFT = "TURN_LEFT";
	public static final String TURN_RIGHT = "TURN_RIGHT";

	private static int singleIdNext;
	private int singleId;

	EnergySource es;

	public Vehicle() {
		this("unknown",0,0);
	}
	public Vehicle(String owner) {
		this(owner,0,0);
	}
	public Vehicle(String owner, double speed, double degree) {
		setId();
		this.owner = owner;
		setSpeed(speed);
		setDegree(degree);
		es = new GasTank(100);
	}
	private void setId() {
		singleId = singleIdNext++;
	}

	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double s) {
		if (s >= 0) speed = s;
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
			degree = maxDeg-(-d%maxDeg);
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

	public void start() {
		if (!es.empty()) {
			System.out.println("started.");
		}
	}

	@Override
	public String toString() {
		String txt = getId() + ": "+getOwner() + "'s vehicle";
		txt += "\n";
		txt += getSpeed()+" (m/s), "+getDegree() + " (degree)";
		return txt;
	}
}