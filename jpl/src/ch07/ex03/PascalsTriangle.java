package ch07.ex03;

// 7.3
// 深さが12のパスカルの三角形を計算するプログラムを作成しなさい。
// 三角形の各行を適切な長さの配列とし、各行の配列を長さ12のint配列の配列に格納しなさい。
// 定数12ではなく、各配列の長さを使用して配列の配列を表示するメソッドにより、
// 結果を表示するようにプログラムしなさい。
// さらに、表示メソッドを変更することなく、12を他の定数に変更してみなさい。

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
