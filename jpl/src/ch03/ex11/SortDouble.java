package ch03.ex11;

// ソートアルゴリズムが、気付かれることなくメトリックスに関して不正をおこなえる
// SortDoubleのセキュリティホールを少なくとも1つ見つけなさい
// そのセキュリティホールをなくすように修正しなさい。
// この場合、ソートアルゴリズムの作成者はmainを書かないと想定してください。

// SimpleSortDoubleに不正操作を書き加えた。
// doSort内でsort(double[])を呼び出すと、Metricsの中身が上書きされる。
// 空の配列を引数にするとMetricsが初期化された状態になるため、
// 本来よりも少ない値になってしまう。

abstract class SortDouble {
	private double[] values;
	private final SortMetrics curMetrics = new SortMetrics();

	// 改善部
	private boolean isCalled = false;

	/** 前ソートをするために呼び出される */
	public final SortMetrics sort(double[] data) {
		// 改善部
		if (isCalled) {
			System.out.println("this sort is already in use.");
			return null;
		}
		isCalled = true;
		values = data;
		curMetrics.init();
		doSort();
		return getMetrics();
	}

	public final SortMetrics getMetrics() {
		return curMetrics.clone();
	}

	/** 拡張したクラスが要素の数を知るため */
	protected final int getDataLength() {
		return values.length;
	}

	/** 拡張したクラスが要素を調べるため */
	protected final double probe(int i) {
		curMetrics.probeCnt++;
		return values[i];
	}

	/** 拡張したクラスが要素を比較するため */
	protected final int compare(int i,int j) {
		curMetrics.compareCnt++;
		double d1 = values[i];
		double d2 = values[j];
		if (d1 == d2)
			return 0;
		else
			return (d1 < d2 ? -1 : 1);
	}

	/** 拡張したクラスが要素を交換するため */
	protected final void swap(int i, int j) {
		curMetrics.swapCnt++;
		double tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	/** 拡張したクラスが実装する -- ソートするのに使用される */
	protected abstract void doSort();
}
