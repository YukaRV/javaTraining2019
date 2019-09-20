package ch01.ex06;

class Fibonacci {
	// 1.6 add title (use constant) output-list of Fibonacci program
	static final String TITLE = "Fibonacci sequence";
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		System.out.println(TITLE);
		System.out.println(lo);
		while (hi < 50) {
			System.out.println(hi);
			hi = lo + hi;	// new-hi
			lo = hi - lo;	// new-lo( = old-hi)
		}
	}
}
