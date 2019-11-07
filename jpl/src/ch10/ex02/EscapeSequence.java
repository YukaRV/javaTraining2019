package ch10.ex02;

// 10.2
// 練習問題10.1で作成したメソッドをswitchを使用して書き直しなさい。

public class EscapeSequence {
	public static void main(String[] args) {
		System.out.println(encodeEscSeq("test\n\\'\"\106"));
	}

	static String encodeEscSeq(String text) {
		String encodedText = "";
		for (int i = 0;i < text.length();i++) {
			char t = text.charAt(i);
			switch(t) {
			case '\n':
				encodedText += "\\n";
				break;
			case '\t':
				encodedText += "\\t";
				break;
			case '\b':
				encodedText += "\\b";
				break;
			case '\r':
				encodedText += "\\r";
				break;
			case '\f':
				encodedText += "\\f";
				break;
			case '\\':
				encodedText += "\\\\";
				break;
			case '\'':
				encodedText += "\'";
				break;
			case '\"':
				encodedText += "\"";
				break;
			default:
				encodedText += t;
			}
		}
		return encodedText;
	}
}