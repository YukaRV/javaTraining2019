package ch10.ex01;

// 10.1
// 文字列のパラメータを取り、その文字列中のすべての特殊文字を、
// 対応するJava言語の表現で置き換えた文字列を返すメソッドを、if-elseを使用して書きなさい。
// たとえば、文字列中に"が含まれていらたら、"を\"で置き換えた文字列を返します。
// (すべての特殊文字は、146頁の7.2.3節に列挙されています。)
// \n 改行(\ u000A)
// \t タブ(\ u0009)
// \b バックスペース(\ u0008)
// \r 復帰(\ )
// \f フォームフィード(\ )
// \\ バックスラッシュ自身(\ )
// \' シングルクォート(\ )
// \" ダブルクォート(\ )

public class EscapeSequence {
	public static void main(String[] args) {
		System.out.println(encodeEscSeq("test\n\\'\"\106"));
	}

	static String encodeEscSeq(String text) {
		String encodedText = "";
		for (int i = 0;i < text.length();i++) {
			char t = text.charAt(i);
			if (t == '\n') {
				encodedText += "\\n";
			} else if (t == '\t') {
				encodedText += "\\t";
			} else if (t == '\b') {
				encodedText += "\\b";
			} else if (t == '\r') {
				encodedText += "\\r";
			} else if (t == '\f') {
				encodedText += "\\f";
			} else if (t == '\\') {
				encodedText += "\\\\";
			} else if (t == '\'') {
				encodedText += "\'";
			} else if (t == '\"') {
				encodedText += "\"";
//			} else if ('\000' < t || t < '\377') {
//				encodedText += t;
			} else {
				encodedText += t;
			}
		}
		return encodedText;
	}
}