package ch03.ex05;

// 他のベンチマークを行う新たな拡張したクラスを作成しなさい。
// たとえば、0からパラメータとして渡された値までループするのに要する時間を計るとか、
class PrintlnBenchmark extends Benchmark{
	/** syso */
	void benchmark() {
		System.out.println();
	}

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		long time = new PrintlnBenchmark().repeat(count);
		System.out.println(count + " method in " + time + " nanoseconds");
	}

}