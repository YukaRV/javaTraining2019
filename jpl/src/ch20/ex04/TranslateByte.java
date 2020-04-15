package ch20.ex04;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// 20.4
// 1行全体が揃うまで待つメソッドを使用して、
// 1度に1行の入力を返すFilterReaderのサブクラスを作成しなさい。


public class TranslateByte extends FilterReader {
	public static void main(String[] args) throws IOException {
		byte from	= (byte)args[0].charAt(0);
		byte to	= (byte)args[1].charAt(0);
		int b;
		InputStreamReader isr = new InputStreamReader(System.in);
		TranslateByte tb = new TranslateByte(isr);
		String x;
		while((x = tb.readLine(from, to)) != null)
			System.out.println(x);
		tb.close();
	}

	public TranslateByte(Reader in) {
		super(in);
	}

	public String readLine(byte from, byte to) throws IOException {
		int b;
		String s = "";
		while ((b = in.read()) != '\r') {
			s += String.valueOf((char)b);
		}
		in.read();
		return s;
	}

}
