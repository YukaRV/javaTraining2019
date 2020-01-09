package ch14.ex04;

public class CalculatableNumber {
	static int val = 0;

	public static synchronized void add(int y) {
		val += y;
		System.out.println(Thread.currentThread().getName()+" add " + y + ": " + val);
	}
}