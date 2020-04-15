package ch20.ex01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


// 20.1
// TranslateByteプログラムをメソッドに書き直して、
// InputStreamの内容をOutputStreamに変換するようにしなさい。
// 変換マッピングとストリームはメソッドのパラメータとして渡します。
// 本章で述べられる各InputStreamとOutputStreamについて、その型のストリームに対して操作を行うために、
// 変換メソッドを使用する新たなmainメソッドを書きなさい。もし入力と出力で一対となるストリームならば、
// 1つのmainメソッドで両方を扱うことができます。

public class TranslateByte {
	public static void main(String[] args) throws IOException {
		byte from	= (byte)args[0].charAt(0);
		byte to	= (byte)args[1].charAt(0);
		translate(from, to, System.in, System.out);
	}

	public static void translate(byte from, byte to, InputStream in, OutputStream out) throws IOException {
		int b;
		while((b = in.read()) != -1) {
			out.write(b == from ? to : b);
		}
	}
}
