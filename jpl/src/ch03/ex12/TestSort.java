package ch03.ex12;

public class TestSort{
	static Double[] testData = {
			0.3, 1.3e-2, 7.9, 3.17
	};
	static String[] testData2 = {
			"Charely", "Bob", "David", "Alice"
	};

	public static void main(String[] args) {
		SortHarness<Double> bsort = new SimpleSortDoubleR();
		SortMetrics metrics = bsort.sort(testData);
		System.out.println("Metrics: " + metrics);
		for (int i = 0;i < testData.length;i++)
			System.out.println("\t" + testData[i]);


		SortHarness<String> bsort2 = new SimpleSortString();
		metrics = bsort2.sort(testData2);
		System.out.println("Metrics: " + metrics);
		for (int i = 0;i < testData.length;i++)
			System.out.println("\t" + testData2[i]);
	}
}
