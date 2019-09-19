package jpl.src.ch02.ex06;

public class LinkedList{
	private Object obj;
	private LinkedList nextObj;
	public void set(Object obj) {
		this.obj = obj;
	}
	public void setNext(LinkedList list) {
		nextObj = list;
	}
	public void setNext(Object obj) {
		nextObj = new LinkedList();
		nextObj.set(obj);
	}
	public Object get() {
		return obj;
	}
	public LinkedList next() {
		return nextObj;
	}

	public static void main(String[] args) {
		Vehicle car1 = new Vehicle();
		car1.setSpeed(100);
		car1.setOwner("Taro");

		Vehicle bike1 = new Vehicle();
		car1.setDegree(45);

		Vehicle bicycle1 = new Vehicle();
		bicycle1.setSpeed(50);
		bicycle1.setDegree(-45);
		bicycle1.setOwner("Hanako");

		LinkedList vs = new LinkedList();
		vs.set(car1);
		vs.setNext(bike1);
		vs.next().setNext(bicycle1);

		LinkedList iter = vs;
		Vehicle v;

		while (iter != null) {
			v = (Vehicle)(iter.obj);
			System.out.println(v.getId() + ": "+v.getOwner() + "'s vehicle");
			System.out.println(v.getSpeed()+" (m/s), "+v.getDegree() + " (degree)");
			System.out.println();

			iter = iter.next();
		}
	}
}