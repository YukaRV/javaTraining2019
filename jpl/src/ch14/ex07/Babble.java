package ch14.ex07;

// 14.7
// Babbleを複数回実行して、結果を調べなさい。常に同じ結果ですか。
// 可能なら、異なるシステムで実行して比較しなさい。
// Did Did DidNot DidNot
// Did DidNot DidNot Did
// Did Did DidNot DidNot
// Did Did DidNot DidNot
// Did Did DidNot DidNot
// Did DidNot DidNot Did
// Did Did DidNot DidNot
// DidNot DidNot Did Did
// Did DidNot DidNot Did
// Did Did DidNot DidNot
// 10回中6回が同じ結果(Did Did DidNot DidNot)

public class Babble extends Thread {
	static boolean doYield;	// 他のスレッドに実行を譲るか？
	static int howOften;		// 表示する回数
	private String word;		// このスレッドの単語

	Babble(String whatToSay) {
		word = whatToSay;
	}

	public void run() {
		for (int i = 0;i < howOften;i++) {
			System.out.println(word);
			if (doYield)
				Thread.yield();	// 他のスレッドを走らせる
		}
	}

	public static void main(String[] args) {
		doYield = new Boolean(args[0]).booleanValue();
		howOften = Integer.parseInt(args[1]);

		// 各単語に対してスレッド作成
		for (int i = 2;i < args.length;i++)
			new Babble(args[i]).start();
	}
}
