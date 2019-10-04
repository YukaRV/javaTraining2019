package ch03.ex12;

// どのようなオブジェクト型もソートできる一般的なSortHarnessクラスを作成しなさい。
// オブジェクトの順序を比較するのに < は使用できないとして、
// オブジェクトの順序を表す方法をどのように提供しますか。

abstract class SortHarness<T extends Comparable<? super T>> {
	private T[] values;
	private final SortMetrics curMetrics = new SortMetrics();

	private boolean isCalled = false;

	/** 前ソートをするために呼び出される */
	public final SortMetrics sort(T[] data) {
		if (isCalled) {
			System.out.println("this sort is already in use.");
			return null;
		}
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
	protected final T probe(int i) {
		curMetrics.probeCnt++;
		return values[i];
	}

	// compareは0,1,-1しか返さないようにする
	/** 拡張したクラスが要素を比較するため */
	protected final int compare(int i,int j) {
		curMetrics.compareCnt++;
		T d1 = (T)values[i];
		T d2 = (T)values[j];
		return (int)Math.signum(d1.compareTo(d2));
	}

	/** 拡張したクラスが要素を交換するため */
	protected final void swap(int i, int j) {
		curMetrics.swapCnt++;
		T tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	/** 拡張したクラスが実装する -- ソートするのに使用される */
	protected abstract void doSort();
}
