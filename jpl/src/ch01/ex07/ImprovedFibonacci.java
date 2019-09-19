package jpl.src.ch01.ex07;

class ImprovedFibonacci {
	// 1.7 rewrite ImprovedFibonacci that i is decrease
	static final int MAX_INDEX = 9;

	/**
	 * add '*' to odd-value
	*/
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		String mark;

		System.out.println("1: "+lo);
		for (int i = MAX_INDEX;i >= 2; i--) {
			if (hi % 2 == 0)
				mark = " *";
			else
				mark = "";
			System.out.println((MAX_INDEX-i+2) + ": " + hi + mark);
			hi = lo + hi;	// new-hi
			lo = hi - lo;	// new-lo( = old-hi)
		}
	}
}
