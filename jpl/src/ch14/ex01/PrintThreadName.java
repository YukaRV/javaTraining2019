package ch14.ex01;


// 14.1
// mainを実行しているスレッドの名前を表示するプログラムを作成しなさい。


public class PrintThreadName {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
	}
}