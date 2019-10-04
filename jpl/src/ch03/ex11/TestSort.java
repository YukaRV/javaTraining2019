package ch03.ex11;

public class TestSort{
	static double[] testData = {
			0.3, 1.3e-2, 7.9, 3.17
	};
//	1: 0.3, 1.3e-2, 7.9, 3.17 swap(0,1)
//	2: 1.3e-2, 0.3, 7.9, 3.17
//	3: 1.3e-2, 0.3, 7.9, 3.17
//	4: 1.3e-2, 0.3, 7.9, 3.17
//	5: 1.3e-2, 0.3, 7.9, 3.17
//	6: 1.3e-2, 0.3, 7.9, 3.17 swap(2,3)
//	7: 1.3e-2, 0.3, 3.17, 7.9

	public static void main(String[] args) {
		SortDouble bsort = new SimpleSortDouble();
		SortMetrics metrics = bsort.sort(testData);
		System.out.println("Metrics: " + metrics);
		for (int i = 0;i < testData.length;i++)
			System.out.println("\t" + testData[i]);
	}
}
