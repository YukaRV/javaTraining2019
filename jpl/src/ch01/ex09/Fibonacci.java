package ch01.ex09;

class Fibonacci {
	// 1.9 use array
	static final String TITLE = "Fibonacci sequence";
	private static int[] fib;
	public static int[] calcFibonacci(int limVal) {
		if (limVal < 2)
			throw new IllegalArgumentException();

		int[] fib = new int[limVal];
		fib[0] = 1;
		fib[1] = 1;
		int i = 2;
		while (i < fib.length) {
			if (fib[i-1] + fib[i-2] >= limVal) break;
			fib[i] = fib[i-1] + fib[i-2];
			i++;
		}
		return fib;
	}
	public static void main(String[] args) {
		System.out.println(TITLE);
		fib = calcFibonacci(50);
		for (int i = 0;i < fib.length;i++) {
			if (fib[i] != 0)
				System.out.println(i+": "+fib[i]);
		}
	}
}
