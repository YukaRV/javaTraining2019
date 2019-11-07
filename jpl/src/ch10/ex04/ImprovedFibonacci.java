package ch10.ex04;

// 10.4①
// 今までの練習問題からforループを使用した問題を数題選んで、
// whileループを使用して書き直しなさい。do-whileループを使用しても書き直すことができますか。
// そのように書き直したりしますか。しないとしたら、なぜですか。

class ImprovedFibonacci {
	static final int MAX_INDEX = 9;

	static ExpNum[] calcFib() {
		ExpNum[] fib = new ExpNum[MAX_INDEX];
		fib[0] = new ExpNum(1);
		fib[1] = new ExpNum(1);
		int i = 2;
		do {
			fib[i] = new ExpNum(fib[i-1].num + fib[i-2].num);
		} while(++i < MAX_INDEX);
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
