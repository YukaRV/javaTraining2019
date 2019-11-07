package ch09.ex02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class CheckBitTest {

	@Test
	public void digitTest() {
		int digit,bits,result;
		for (int i = 0;i < 30;i++) {
			digit = i;
			bits = (int)(Math.pow(2, digit))-1;
			result = CheckBit.bitCount(bits);
			assertEquals(digit, result);
		}
   }

}
