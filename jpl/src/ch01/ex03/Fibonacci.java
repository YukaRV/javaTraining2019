package ch01.ex03;

class Fibonacci {
	// 1.3 add title output-list of Fibonacci program
	public static void main(String[] args) {
		int lo = 1;
		int hi = 1;
		System.out.println("Fibonacci sequence");
		System.out.println(lo);
		while (hi < 50) {
			System.out.println(hi);
			hi = lo + hi;	// new-hi
			lo = hi - lo;	// new-lo( = old-hi)
		}
	}
}
