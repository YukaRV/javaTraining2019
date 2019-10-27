package ch06.ex02;

// 6.2
// Q. TURN_LEFTとTURN_RIGHTを表すenumを使用して、練習問題2.17をやり直しなさい。
//    enumを使用することにどのような利点がありますか。

// A. 型の名前が文字列になっているため、対応させた文字列を用意する必要がない。
//    そのため、if文で分岐する必要がないためコード的な抜けが起きにくい。
//    また、enumとして定義した値しか入力できないので、望まない値を入れさせない。


public class Vehicle {
	private double speed,degree;
	private String owner = "unknown";
	enum TURN {TURN_LEFT,TURN_RIGHT};

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
			degree = maxDeg-(-d%maxDeg);
	}
	public void turn(double d) {
		setDegree(degree+d);
	}
	public void turn(TURN d) {
		System.out.println(d);
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
		car1.turn(Vehicle.TURN.TURN_LEFT);
		System.out.println(car1);
	}
}