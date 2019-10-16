package ch06.ex05;

import java.awt.Color;

// 6.5
// 練習問題6.4で、getColorをabstractとして、各enum定数が
// 正しいColorオブジェクトを返すように定数固有のメソッドを定義しなさい。
// Colorオブジェクトを返すために、定数固有のメソッドを使用することを推奨しますか。

// 推奨はしない。
// 今回はチェスの駒のように複雑な動きをするものであれば、固有のふるまいをさせるために
// 別クラスを定義したり、それぞれで実装内容を書くのが好ましい。
// 信号の場合、歩道者信号の点滅を含めた情報を渡すのであれば、あるとよい。
// 今回は色のみを渡すenumであるため、個別にオーバーライドさせると実装が複雑になってしまうため
// 推奨しない。

enum TrafficSignal {
	BLUE{
		@Override
		Color getColor() {
			return Color.BLUE;
		}
	},
	YELLOW{
		@Override
		Color getColor() {
			return Color.YELLOW;
		}
	},
	RED{
		@Override
		Color getColor() {
			return Color.RED;
		}
	};

	abstract Color getColor();
}
