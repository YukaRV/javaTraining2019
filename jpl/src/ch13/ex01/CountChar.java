package ch13.ex01;

// 13.1
// 文字列中に、指定された文字が出現する回数を数えるメソッドを書きなさい。


public class CountChar {
	public static int countChar(String str, char c) {
		int i = str.indexOf(c);
		if (i == -1) return 0;
		int count = 0;
		while (i < str.length()) {
			if (str.charAt(i++) == c) count++;
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(countChar("abcdefgabcdef", 'a'));
	}
}