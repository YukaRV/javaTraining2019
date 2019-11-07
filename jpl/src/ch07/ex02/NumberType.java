package ch07.ex02;

// 9.1
// 2つの無限大のオペランドに対して演算子+,-,*,/を使用するプログラムを作成して、
// 結果を表示しなさい。どちらも同じ符号の値と、異なる符号の値の両方を試しなさい。

// 和差では、-を+に直したとき符号が同じときは計算できる。
// 積商では、/を*に直したとき大きさが同じときは計算がされる。
//

public class NumberType {

	public static void main(String[] args) {
		final double posInf = Double.POSITIVE_INFINITY;
		final double negInf = Double.NEGATIVE_INFINITY;
		System.out.println("posInf : posInf");
		System.out.println(posInf+posInf);
		System.out.println(posInf-posInf);
		System.out.println(posInf*posInf);
		System.out.println(posInf/posInf);
		System.out.println("posInf : negInf");
		System.out.println(posInf+negInf);
		System.out.println(posInf-negInf);
		System.out.println(posInf*negInf);
		System.out.println(posInf/negInf);
	}
}
