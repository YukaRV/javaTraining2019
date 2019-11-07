package ch10.ex05;

// 10.5
// 2つのcharを引数にとり、それらの文字とそれらの文字間の文字を表示するメソッドを書きなさい。

public class CharsBetween2Chars {
	public static void main(String[] args) {
		printCharsBetween2Chars('a', 'x');
	}
	static void printCharsBetween2Chars(char charA, char charB) {
		System.out.print("("+charA+","+charB+") = ");
		for (int i = charA+1;i < charB;i++) {
			System.out.print((char)i);
		}
	}

}