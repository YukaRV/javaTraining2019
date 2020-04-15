package ch20.ex03;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutputStream extends FilterOutputStream {
	public EncryptOutputStream(OutputStream out) {
		super(out);
	}

	public void write(int b) throws IOException {
		char c = (char)(b^Integer.MAX_VALUE);
		super.write(c);
	}

}
