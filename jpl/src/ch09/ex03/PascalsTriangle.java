package ch09.ex03;

// 9.3
// 本章で学んだ演算子を使用して、練習問題7.3の解答をより明瞭、あるいは、
// より簡潔に書くことができるか検討しなさい。

// 既に使用している

public class PascalsTriangle {
	public static void main(String[] args) {
		int[][] pascalTriangle = getPascalTriangle(12);
		printIntArrayInArray(pascalTriangle);
	}

	public static int[][] getPascalTriangle(int length) {
		int[][] pascalTriangle = new int[length][];
		pascalTriangle[0] = new int[1];
		pascalTriangle[0][0] = 1;
		for (int i = 0;i < length-1;i++) {
			int curWidth = pascalTriangle[i].length;
			pascalTriangle[i+1] = new int[curWidth+1];
			for (int j = 0;j < curWidth;j++) {
				int x = pascalTriangle[i][j];
				pascalTriangle[i+1][j] += x;
				pascalTriangle[i+1][j+1] += x;
			}
		}
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
