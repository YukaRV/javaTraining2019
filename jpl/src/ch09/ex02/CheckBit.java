package ch09.ex02;

// 9.2
// ビット操作演算子だけを使用し(すなわち、Integer.bitCountを使用しないで)、
// 渡されたintで、1となっているビット数を調べるメソッドを作成しなさい。
// 作成した解答と公開されているアルゴリズムを比較しなさい。
// 公開されているアルゴリズムの1つに関しては、670頁の｢一般的なプログラミング技法｣の
// 関連する書籍を参照してください。

public class CheckBit {

	public static void main(String[] args) {
		int x = (int)(Math.pow(2, 30))-1;
		System.out.println("x = " + x);
		System.out.println(Integer.bitCount(x));
		System.out.println(bitCount(x));
	}
	static int bitCount(int bits) {
	    int count  = 0;
	    int mask = 1;

	    while (bits != 0) {
	    	count += (bits & mask);
	    	bits >>= 1;
	    }

	    return count;
	}


}
