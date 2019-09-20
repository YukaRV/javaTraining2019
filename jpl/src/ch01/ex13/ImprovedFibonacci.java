package ch01.ex13;

class ImprovedFibonacci {
	// 1.13 use printf
	static final int MAX_INDEX = 9;

	static ExpNum[] calcFib() {
		if (MAX_INDEX <= 0) return null;
		ExpNum[] fib = new ExpNum[MAX_INDEX];
		fib[0] = new ExpNum(1);
		if (MAX_INDEX == 1) return fib;
		fib[1] = new ExpNum(1);

		for (int i = 2;i < MAX_INDEX; i++) {
			fib[i] = new ExpNum(fib[i-1].num + fib[i-2].num);
		}
		return fib;
	}
	/**
	 * add '*' to odd-value
	*/
	public static void main(String[] args) {
		String mark;
		ExpNum[] fib = calcFib();
		for (int i = 0;i < MAX_INDEX; i++) {
			if (fib[i].odd)
				mark = " *";
			else
				mark = "";
			System.out.printf("%d:%d%s%n",(i+1), fib[i].num, mark);
		}
	}
}
