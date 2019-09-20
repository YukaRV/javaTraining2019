package ch01.ex12;

class ImprovedFibonacci {
	// 1.12 use String object for sysout
	static final int MAX_INDEX = 9;

	static ExpNum[] calcFib() {
		ExpNum[] fib = new ExpNum[MAX_INDEX];
		fib[0] = new ExpNum(1);
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
		String text;
		ExpNum[] fib = calcFib();
		for (int i = 0;i < MAX_INDEX; i++) {
			text = (i+1)+":" + fib[i].num;
			if (fib[i].odd)
				text += " *";
			else
				text += "";
			System.out.println(text);
		}
	}
}
