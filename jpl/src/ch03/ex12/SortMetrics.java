package ch03.ex12;

public class SortMetrics implements Cloneable{
	public long probeCnt,		// 単純なデータの値調査
					compareCnt,	// 2つの要素の比較
					swapCnt;	// 2つの要素の交換

	public void init() {
		probeCnt = swapCnt = compareCnt = 0;
	}

	@Override
	public String toString() {
		return probeCnt + " probes " +
				compareCnt + " compares " +
				swapCnt + " swaps";
	}

	/** このクラスは、cloneをサポートしている */
	public SortMetrics clone() {
		try {
			// デフォルトの仕組みで十分
			return (SortMetrics)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

}
