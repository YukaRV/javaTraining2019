package ch03.ex11;

public class SimpleSortDouble extends SortDouble{

	public SimpleSortDouble() {
		super();
	}

	static boolean sorted = false;
	@Override
	protected void doSort() {
		for (int i = 0;i < getDataLength();i++) {
			for (int j = i+1;j < getDataLength();j++) {
//				System.out.print("["+probe(0)+","+probe(1)+","+probe(2)+","+probe(3)+"]");
				if (compare(i,j) > 0) {
//					System.out.print(" *");
					swap(i,j);
				}
//				System.out.println();
			}
		}
		// ---- 不正操作 ----
		if (sorted) return;
		sorted = true;
		double[] x = {};
		sort(x);
		// ---- 不正終了 ----
	}

}
