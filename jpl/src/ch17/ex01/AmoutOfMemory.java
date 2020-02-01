package ch17.ex01;

// 17.1
// 起動時と多くのオブジェクトを生成した後で、利用可能なメモリ量を調べるプログラムを書きなさい。
// ガーベッジコレクタを呼び出して、空きメモリ量がどのように変化するかを調べなさい。
// もちろん、新たに生成されたオブジェクトへの参照を間違いなく保持していないようにしてください。

public class AmoutOfMemory {
	public static void main(String[] args) {
		// 初期状態
		System.out.println(Runtime.getRuntime().freeMemory());
		// 初期状態でガーベッジコレクタを呼び出してみる
		fullGC();
		System.out.println(Runtime.getRuntime().freeMemory());
		// 大量に無駄なオブジェクトを生成
		generateUselessObject();
		System.out.println(Runtime.getRuntime().freeMemory());
		// ガーベッジコレクタを呼び出し
		fullGC();
		System.out.println(Runtime.getRuntime().freeMemory());

	}
	public static void generateUselessObject() {
		int length = 10000;
		String[] strs = new String[length];
		int[] integers = new int[length];
		for (int i = 0;i < length;i++) {
			strs[i] = "test";
			integers[i] = Integer.MAX_VALUE;
		}
	}

	public static void fullGC() {
		Runtime rt = Runtime.getRuntime();
		long isFree = rt.freeMemory();
		long wasFree;
		do {
			wasFree = isFree;
			rt.runFinalization();
			rt.gc();
			isFree = rt.freeMemory();
		} while (isFree > wasFree);
	}
}
