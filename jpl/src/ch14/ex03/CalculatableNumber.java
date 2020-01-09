package ch14.ex03;

public class CalculatableNumber {
	int val = 0;

	public synchronized void add(int y) {
		val += y;
		System.out.println(Thread.currentThread().getName()+" add " + y + ": " + val);
	}
}