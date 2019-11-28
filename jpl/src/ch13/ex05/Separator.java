package ch13.ex05;

// 13.5
// 10進数を含む文字列を、右から3桁ごとにカンマで区切られた数に
// 変換するメソッドを書きなさい。たとえば、文字列"1543729"が与えられたら、
// そのメソッドは、文字列"1,543,729"を返します。

public class Separator<E> {
	public static String digitSeparator(String numStr) {
		int interval = 3;
		int i = numStr.length()-interval;
		StringBuilder numStrB = new StringBuilder(numStr);
		while (i>0) {
			numStrB.insert(i, ",");
			i -= interval;
		}
		return numStrB.toString();
	}

	public static void main(String[] args) {
		String numStr = "1234567890";
		System.out.println(numStr);
		System.out.println(digitSeparator(numStr));
	}
}