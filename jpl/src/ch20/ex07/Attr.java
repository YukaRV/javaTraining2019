package ch20.ex07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 20.7
// 第3章のAttrクラスへ、DataOutputStreamにオブジェクトの内容を書き込むメソッドを追加しなさい。
// また、DataInputStreamから状態を読み込むコンストラクタを追加しなさい。

public class Attr<T> {
	private final String name;
	private T value = null;

	public Attr(String name) {
		this.name = name;
	}

	public Attr(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public T setValue(T newValue) {
		T oldVal = value;
		value = newValue;
		return oldVal;
	}

	public String toString() {
		return name + "='" + value + "'";
	}
	public Attr<T> read(String fileName) throws IOException{
		InputStream fin = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fin);
		String[] data = new String[2];
		for (int i = 0;i < data.length;i++) data[i] = "";
		int i = 0;
		while(true) {
			char c = in.readChar();
			if(c == '\n') {
				break;
			} if (c == ',') {
				i++;
			} else {
				data[i] += String.valueOf((char)c);
			}
		}
		in.close();
		Attr<T> attr = new Attr<>(data[0],(T)value);
		return attr;
	}
	public boolean write(String fileName) throws IOException{
		OutputStream fout = new FileOutputStream(fileName);
		DataOutputStream out = new DataOutputStream(fout);
		out.writeChars(name);
		out.writeChar(',');
		out.writeChars(String.valueOf(value));
		out.writeChar('\n');
		out.close();
		return true;
	}
}
