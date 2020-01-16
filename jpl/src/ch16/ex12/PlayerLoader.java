package ch16.ex12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PlayerLoader extends ClassLoader{
	public Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] buf = bytesForClass(name);
			return defineClass(name, buf, 0, buf.length);
		} catch (IOException e) {
			throw new ClassNotFoundException(e.toString());
		}
	}

	// bytesForClassと他のメソッド
	protected byte[] bytesForClass(String name) throws IOException, ClassNotFoundException {
		FileInputStream in = null;
		try {
			in = streamFor(name + ".class");
			int length = in.available();	// バイト数を得る
			if (length == 0)
				throw new ClassNotFoundException(name);
			byte[] buf = new byte[length];
			in.read(buf);
			return buf;
		} finally {
			if (in != null)
				in.close();
		}
	}

	private FileInputStream streamFor(String className) {
		FileInputStream fi;
		try {
			fi = new FileInputStream(className);
			return fi;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
