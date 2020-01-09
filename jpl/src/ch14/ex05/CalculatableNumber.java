package ch14.ex05;

public class CalculatableNumber {
	int val = 0;

	public void add(int y) {
		synchronized (CalculatableNumber.this) {
			val += y;
			System.out.println(Thread.currentThread().getName()+" add " + y + ": " + val);
		}
	}
}