package ch04.ex02;

// 4.2
// 99頁の練習問題3.12の解答を、最初にインタフェースを使用して書いていなければ、
// インタフェースを使用して書き直しなさい。
// 済。3.12のコピー

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
