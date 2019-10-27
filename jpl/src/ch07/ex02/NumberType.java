package ch07.ex02;

// 7.2
// 数値である各基本データ型のフィールドを宣言しているクラスを作成して、
// 異なるリテラル形式を使用して値を代入してみなさい。
// たとえば、intフィールドに3.5fを代入してみるとか。
// どのリテラルがどのフィールド型と一緒に使用できますか。
// 値の大きさを変えて、どのような影響があるか調べてみなさい。

// char 0~65535
// byte -128~127
// short -32768~32767
// int -2147483648~2147483647
// のため、整数型ではその範囲に収まるもののみそのまま代入可能である。
// 収まっていなくてもキャストすれば表示できる。=値が変わる
// 実数型も同様の条件で代入の可不可が決まる。
// 整数型に実数をキャストなしに代入することはできない。
// 整数型、実数型に関わらず、maxもminも代入先の範囲外であった場合
// 範囲内の値(今回の場合10)も全て代入不可となる。

public class NumberType {
	static final char   CHAR_VALUE = '\n',
					MIN_CHAR_VALUE = Character.MIN_VALUE,
					MAX_CHAR_VALUE = Character.MAX_VALUE;
	static final byte   BYTE_VALUE = (byte)10,
					MIN_BYTE_VALUE = Byte.MIN_VALUE,
					MAX_BYTE_VALUE = Byte.MAX_VALUE;
	static final short  SHORT_VALUE = (short)10,
					MIN_SHORT_VALUE = Short.MIN_VALUE,
					MAX_SHORT_VALUE = Short.MAX_VALUE;
	static final int    INT_VALUE = (int)10,
					MIN_INT_VALUE = Integer.MIN_VALUE,
					MAX_INT_VALUE = Integer.MAX_VALUE;
	static final long   LONG_VALUE = 10l,
					MIN_LONG_VALUE = Long.MIN_VALUE,
					MAX_LONG_VALUE = Long.MAX_VALUE;
	static final float  FLOAT_VALUE = 10f,
					MIN_FLOAT_VALUE = Float.MIN_VALUE,
					MAX_FLOAT_VALUE = Float.MAX_VALUE;
	static final double DOUBLE_VALUE = 10d,
					MIN_DOUBLE_VALUE = Double.MIN_VALUE,
					MAX_DOUBLE_VALUE = Double.MAX_VALUE;

	public static void main(String[] args) {
		set2CharTest();
		set2ByteTest();
		set2ShortTest();
		set2IntTest();
		set2LongTest();
		set2FloatTest();
		set2DoubleTest();
	}

	public static void set2CharTest() {
		System.out.println("--- char test ---");
		char c;
		c = CHAR_VALUE;
		c = BYTE_VALUE;
		c = SHORT_VALUE;
		c = INT_VALUE;
//		c = LONG_VALUE;// キャストは可能
//		c = FLOAT_VALUE;// キャストは可能
//		c = DOUBLE_VALUE;// キャストは可能

		c = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+c+":"+MIN_CHAR_VALUE);
		c = MAX_CHAR_VALUE;
		System.out.println("char max...\n "+c+":"+MAX_CHAR_VALUE);

//		c = MIN_BYTE_VALUE;// キャストは可能
		c = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+c+":"+MAX_BYTE_VALUE);

//		c = MIN_SHORT_VALUE;// キャストは可能
		c = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+c+":"+MAX_SHORT_VALUE);

//		c = MIN_INT_VALUE;// キャストは可能
//		c = MAX_INT_VALUE;// キャストは可能
	}

	public static void set2ByteTest() {
		System.out.println("--- byte test ---");
		byte b;
		b = CHAR_VALUE;
		b = BYTE_VALUE;
		b = SHORT_VALUE;
		b = INT_VALUE;
//		b = LONG_VALUE;// キャストは可能
//		b = FLOAT_VALUE;// キャストは可能
//		b = DOUBLE_VALUE;// キャストは可能

		b = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+b+":"+MIN_CHAR_VALUE);
//		b = MAX_CHAR_VALUE;// キャストは可能

		b = MIN_BYTE_VALUE;// キャストは可能
		System.out.println("byte min...\n "+b+":"+MIN_BYTE_VALUE);
		b = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+b+":"+MAX_BYTE_VALUE);

//		b = MIN_SHORT_VALUE;// キャストは可能
//		b = MAX_SHORT_VALUE;

//		b = MIN_INT_VALUE;// キャストは可能
//		b = MAX_INT_VALUE;// キャストは可能
	}

	public static void set2ShortTest() {
		System.out.println("--- short test ---");
		short s;
		s = CHAR_VALUE;
		s = BYTE_VALUE;
		s = SHORT_VALUE;
		s = INT_VALUE;
//		s = LONG_VALUE;// キャストは可能
//		s = FLOAT_VALUE;// キャストは可能
//		s = DOUBLE_VALUE;// キャストは可能

		s = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+s+":"+MIN_CHAR_VALUE);
//		s = MAX_CHAR_VALUE;// キャストは可能

		s = MIN_BYTE_VALUE;
		System.out.println("byte min...\n "+s+":"+MIN_BYTE_VALUE);
		s = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+s+":"+MAX_BYTE_VALUE);

		s = MIN_SHORT_VALUE;
		System.out.println("short min...\n "+s+":"+MIN_SHORT_VALUE);
		s = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+s+":"+MAX_SHORT_VALUE);

//		s = MIN_INT_VALUE;// キャストは可能
//		s = MAX_INT_VALUE;// キャストは可能
	}

	public static void set2IntTest() {
		System.out.println("--- int test ---");
		int i;
		i = CHAR_VALUE;
		i = BYTE_VALUE;
		i = SHORT_VALUE;
		i = INT_VALUE;
//		i = LONG_VALUE;// キャストは可能
//		i = FLOAT_VALUE;// キャストは可能
//		i = DOUBLE_VALUE;// キャストは可能

		i = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+i+":"+MIN_CHAR_VALUE);
		i = MAX_CHAR_VALUE;
		System.out.println("char max...\n "+i+":"+MAX_CHAR_VALUE);

		i = MIN_BYTE_VALUE;
		System.out.println("byte min...\n "+i+":"+MIN_BYTE_VALUE);
		i = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+i+":"+MAX_BYTE_VALUE);

		i = MIN_SHORT_VALUE;
		System.out.println("short min...\n "+i+":"+MIN_SHORT_VALUE);
		i = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+i+":"+MAX_SHORT_VALUE);

		i = MIN_INT_VALUE;
		System.out.println("int min...\n "+i+":"+MIN_INT_VALUE);
		i = MAX_INT_VALUE;
		System.out.println("int max...\n "+i+":"+MAX_INT_VALUE);
	}

	public static void set2LongTest() {
		System.out.println("--- long test ---");
		long l;
		l = CHAR_VALUE;
		l = BYTE_VALUE;
		l = SHORT_VALUE;
		l = INT_VALUE;
		l = LONG_VALUE;
//		l = FLOAT_VALUE;// キャストは可能
//		l = DOUBLE_VALUE;// キャストは可能

		l = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+l+":"+MIN_CHAR_VALUE);
		l = MAX_CHAR_VALUE;
		System.out.println("char max...\n "+l+":"+MAX_CHAR_VALUE);

		l = MIN_BYTE_VALUE;
		System.out.println("byte min...\n "+l+":"+MIN_BYTE_VALUE);
		l = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+l+":"+MAX_BYTE_VALUE);

		l = MIN_SHORT_VALUE;
		System.out.println("short min...\n "+l+":"+MIN_SHORT_VALUE);
		l = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+l+":"+MAX_SHORT_VALUE);

		l = MIN_INT_VALUE;
		System.out.println("int min...\n "+l+":"+MIN_INT_VALUE);
		l = MAX_INT_VALUE;
		System.out.println("int max...\n "+l+":"+MAX_INT_VALUE);

		l = MIN_LONG_VALUE;
		System.out.println("long min...\n "+l+":"+MIN_LONG_VALUE);
		l = MAX_LONG_VALUE;
		System.out.println("long max...\n "+l+":"+MAX_LONG_VALUE);
	}

	public static void set2FloatTest() {
		System.out.println("--- float test ---");
		float f;
		f = CHAR_VALUE;
		f = BYTE_VALUE;
		f = SHORT_VALUE;
		f = INT_VALUE;
		f = LONG_VALUE;
		f = FLOAT_VALUE;
//		f = DOUBLE_VALUE;// キャストは可能

		f = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+f+":"+MIN_CHAR_VALUE);
		f = MAX_CHAR_VALUE;
		System.out.println("char max...\n "+f+":"+MAX_CHAR_VALUE);

		f = MIN_BYTE_VALUE;
		System.out.println("byte min...\n "+f+":"+MIN_BYTE_VALUE);
		f = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+f+":"+MAX_BYTE_VALUE);

		f = MIN_SHORT_VALUE;
		System.out.println("short min...\n "+f+":"+MIN_SHORT_VALUE);
		f = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+f+":"+MAX_SHORT_VALUE);

		f = MIN_INT_VALUE;
		System.out.println("int min...\n "+f+":"+MIN_INT_VALUE);
		f = MAX_INT_VALUE;
		System.out.println("int max...\n "+f+":"+MAX_INT_VALUE);

		f = MIN_LONG_VALUE;
		System.out.println("long min...\n "+f+":"+MIN_LONG_VALUE);
		f = MAX_LONG_VALUE;
		System.out.println("long max...\n "+f+":"+MAX_LONG_VALUE);

		f = MIN_FLOAT_VALUE;
		System.out.println("float min...\n "+f+":"+MIN_FLOAT_VALUE);
		f = MAX_FLOAT_VALUE;
		System.out.println("float max...\n "+f+":"+MAX_FLOAT_VALUE);
	}

	public static void set2DoubleTest() {
		System.out.println("--- double test ---");
		double d;
		d = CHAR_VALUE;
		d = BYTE_VALUE;
		d = SHORT_VALUE;
		d = INT_VALUE;
		d = LONG_VALUE;
		d = FLOAT_VALUE;
		d = DOUBLE_VALUE;

		d = MIN_CHAR_VALUE;
		System.out.println("char min...\n "+d+":"+MIN_CHAR_VALUE);
		d = MAX_CHAR_VALUE;
		System.out.println("char max...\n "+d+":"+MAX_CHAR_VALUE);

		d = MIN_BYTE_VALUE;
		System.out.println("byte min...\n "+d+":"+MIN_BYTE_VALUE);
		d = MAX_BYTE_VALUE;
		System.out.println("byte max...\n "+d+":"+MAX_BYTE_VALUE);

		d = MIN_SHORT_VALUE;
		System.out.println("short min...\n "+d+":"+MIN_SHORT_VALUE);
		d = MAX_SHORT_VALUE;
		System.out.println("short max...\n "+d+":"+MAX_SHORT_VALUE);

		d = MIN_INT_VALUE;
		System.out.println("int min...\n "+d+":"+MIN_INT_VALUE);
		d = MAX_INT_VALUE;
		System.out.println("int max...\n "+d+":"+MAX_INT_VALUE);

		d = MIN_LONG_VALUE;
		System.out.println("long min...\n "+d+":"+MIN_LONG_VALUE);
		d = MAX_LONG_VALUE;
		System.out.println("long max...\n "+d+":"+MAX_LONG_VALUE);

		d = MIN_FLOAT_VALUE;
		System.out.println("float min...\n "+d+":"+MIN_FLOAT_VALUE);
		d = MAX_FLOAT_VALUE;
		System.out.println("float max...\n "+d+":"+MAX_FLOAT_VALUE);

		d = MIN_DOUBLE_VALUE;
		System.out.println("double min...\n "+d+":"+MIN_DOUBLE_VALUE);
		d = MAX_DOUBLE_VALUE;
		System.out.println("double max...\n "+d+":"+MAX_DOUBLE_VALUE);
	}
}