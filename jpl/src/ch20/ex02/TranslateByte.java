package ch20.ex02;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// 20.2
// TranslateByteクラスをフィルターとして書き直しなさい。

public class TranslateByte extends FilterReader {
	public static void main(String[] args) throws IOException {
		byte from	= (byte)args[0].charAt(0);
		byte to	= (byte)args[1].charAt(0);
		int b;
		InputStreamReader isr = new InputStreamReader(System.in);
		TranslateByte tb = new TranslateByte(isr);
		while((b = tb.read(from,to)) != -1)
			System.out.write((char)b);
		tb.close();
	}

	public TranslateByte(Reader in) {
		super(in);
	}

	public int read(byte from, byte to) throws IOException {
		int stream = super.read();
		return (stream == from ? to : stream);
	}

}
