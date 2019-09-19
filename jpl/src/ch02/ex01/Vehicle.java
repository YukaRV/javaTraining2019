package jpl.src.ch02.ex01;

public class Vehicle {
	private double speed,degree;
	private String owner;

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
}