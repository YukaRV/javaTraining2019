package ch10.ex04;

// 10.4②
// 今までの練習問題からforループを使用した問題を数題選んで、
// whileループを使用して書き直しなさい。do-whileループを使用しても書き直すことができますか。
// そのように書き直したりしますか。しないとしたら、なぜですか。

public class PascalsTriangle {
	public static void main(String[] args) {
		int[][] pascalTriangle = getPascalTriangle(12);
		printIntArrayInArray(pascalTriangle);
	}

	public static int[][] getPascalTriangle(int length) {
		int[][] pascalTriangle = new int[length][];
		pascalTriangle[0] = new int[1];
		pascalTriangle[0][0] = 1;
		int i=0,j=0;
		do {
			int curWidth = pascalTriangle[i].length;
			pascalTriangle[i+1] = new int[curWidth+1];
			do {
				int x = pascalTriangle[i][j];
				pascalTriangle[i+1][j] += x;
				pascalTriangle[i+1][j+1] += x;
			} while(++j < curWidth);
			j=0;
		} while(++i < length-1);
		return pascalTriangle;
	}

	public static void printIntArrayInArray(int[][] array) {
		for (int i = 0;i < array.length;i++) {
			System.out.print(String.format("%02d",i)+":[ ");
			for (int j = 0;j < array[i].length;j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println("]");
		}
	}
}
