package ch07.ex01;

// 7.1
// 遊びで、Unicodeエスケープシーケンスだけを使用して
// "Hello, World"プログラムを書きなさい。
// 文字のみ使用

public class HelloWorld_onlyText {

	public static void main(String[] args) {
		String hello = "\u0048\u0065\u006C\u006C\u006F";
		String camma = "\u002C";
		String space = "\u0020";
		String world = "\u0057\u006F\u0072\u006C\u0064";
		System.out.println(hello + camma + space + world);
	}
}
