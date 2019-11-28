package ch13.ex02;

// 13.2
// 文字列中に、特定文字列が出現する回数を数えるメソッドを書きなさい。


public class CountString {
	public static int countString(String str, String str2) {
		if (!str.contains(str2)) return 0;
		int i = 0;
		int count = 0;
		while (i < str.length()) {
			i = str.indexOf(str2);
			if (i == -1) return count;
			count++;
			str = str.substring(i+1);
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(countString("abcdefgabcdef", "efg"));
	}
}