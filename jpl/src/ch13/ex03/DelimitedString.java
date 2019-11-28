package ch13.ex03;

import java.util.ArrayList;

// 13.3
// 本文中のdelimitedStringメソッドは、
// 1つの入力文字列に1つの区切られた文字列しかないと仮定しています。
// すべての区切られた文字列を取り出して配列にして返すバージョンを作成しなさい。

public class DelimitedString {
	public static String[] delimitedStrings(String from, char start, char end) {
		ArrayList<String> strList = new ArrayList<String>();
		int startPos = from.indexOf(start);
		int endPos;
		while (startPos != -1) {
			endPos = from.indexOf(end);
			if (endPos == -1) {
				strList.add(from.substring(startPos));
			} else {
				while (endPos != -1) {
					if (startPos <= endPos) {
						strList.add(from.substring(startPos, endPos+1));
					}
					endPos = from.indexOf(end, endPos+1);
				}
			}
			startPos = from.indexOf(start, startPos+1);
			endPos = 0;
		}
		String[] strArray = new String[strList.size()];
		return strList.toArray(strArray);
	}
	public static String delimitedString(String from, char start, char end) {
		int startPos = from.indexOf(start);
		int endPos = from.lastIndexOf(end);
		if (startPos == -1)			 // 開始文字が見つからない
			return null;
		else if (endPos == -1)		// 終了文字が見つからない
			return from.substring(startPos);
		else if (startPos > endPos) // 開始文字が終了文字の後にある
			return null;
		else						 // 開始文字と終了文字が見つかった
		return from.substring(startPos, endPos+1);
	}

	public static void main(String[] args) {
		String[] result = delimitedStrings("abcdefgabcdef", 'c', 'j');
		for (int i = 0;i < result.length;i++) {
			System.out.println(result[i]);
		}
	}
}