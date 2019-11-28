package ch13.ex06;

// 13.6
// 区切り文字と、区切り文字間の桁数を指定するパラメータを受け付けるように
// メソッドを修正しなさい。

public class Separator<E> {
	public static String digitSeparator(String numStr, char c, int digit) {
		if (digit < 1) return numStr;
		int interval = digit;
		int i = numStr.length()-interval;
		StringBuilder numStrB = new StringBuilder(numStr);
		while (i>0) {
			numStrB.insert(i, c);
			i -= interval;
		}
		return numStrB.toString();
	}

	public static void main(String[] args) {
		String numStr = "1234567890";
		System.out.println(numStr);
		System.out.println(digitSeparator(numStr,'A',2));
	}
}