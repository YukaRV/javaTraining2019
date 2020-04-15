package ch20.ex03;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

// 20.3
// バイトを何らかの値とXORするなど、どのようなアルゴリズムでもいいので、
// バイトを暗号化する一組のFilterストリームクラスである
// DecryptInputStreamとEncryptOutputStreamを作成しなさい。
// DecryptInputStreamは、EncryptOutputStreamクラスが生成したバイトを復号化します。

public class DecryptInputStream extends FilterInputStream {
	public static void main(String[] args) throws IOException {
		byte from	= (byte)args[0].charAt(0);
		byte to	= (byte)args[1].charAt(0);
		int b;
		DecryptInputStream di = new DecryptInputStream(System.in);
		EncryptOutputStream eo = new EncryptOutputStream(System.out);
		while((b = di.read(from,to)) != -1)
			eo.write(b);
		di.close();
		eo.close();
	}

	public DecryptInputStream(InputStream in) {
		super(in);
	}

	public int read(byte from, byte to) throws IOException {
		int stream = super.read();
		return stream^Integer.MAX_VALUE;
	}

}
