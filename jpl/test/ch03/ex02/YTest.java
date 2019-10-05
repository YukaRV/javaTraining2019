package ch03.ex02;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class YTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

	String brRN = "\r\n";
	String brN = "\n";
	// mainのテスト
	@Test
	public void mainTest() {
		String[] args = {};
		Y.main(args);
		String resultStr = outContent.toString();
		String expectStr = "初期値" + brRN +
				"xMask yMask fullMask" + brRN +
				"0x0000 0x0000 0x0000" + brN +
				"4. Xのフィールドを初期化" + brRN +
				"xMask yMask fullMask" + brRN +
				"0x00ff 0x0000 0x0000" + brN +
				"5. Xのコンストラクタが実行される" + brRN +
				"xMask yMask fullMask" + brRN +
				"0x00ff 0x0000 0x00ff" + brN +
				"6. Yのフィールドを初期化" + brRN +
				"xMask yMask fullMask" + brRN +
				"0x00ff 0xff00 0x00ff" + brN +
				"7. Yのコンストラクタを実行" + brRN +
				"xMask yMask fullMask" + brRN +
				"0x00ff 0xff00 0xffff" + brN;
		System.out.println(diff(expectStr,resultStr));
		assertEquals(expectStr, resultStr);
	}

	public int diff(String expect,String target) {
		for (int i = 0;i < target.length();i++) {
			if (i == expect.length() || expect.charAt(i) != target.charAt(i)) {
				char d = expect.charAt(i);
				return i;
			}
		}
		if (expect.length() != target.length()) return target.length();
		return -1;
	}

}

