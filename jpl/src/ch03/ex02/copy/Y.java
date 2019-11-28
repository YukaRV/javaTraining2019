package ch03.ex02.copy;

// 3.2
// テキストのクラスX,Yを入力して、各マスクの値の変化を表示するための出力文を追加しなさい。
// mainメソッドを追加し、実行して結果を見てください。

// 191115 追記: printVariableをX,Y共に修正
// 				XでyMaskに直接0を入れる必要が内容変更

public class Y extends X{
	protected int yMask = 0xff00;

	public Y() {
		printVariable("6. Yのフィールドを初期化");
		fullMask |= yMask;
		printVariable("7. Yのコンストラクタを実行");
	}

	@Override
	public void printVariable(String title) {
		System.out.println(title);
		System.out.println("xMask yMask fullMask");
		System.out.printf("0x%04x 0x%04x 0x%04x\n",super.xMask,yMask,super.fullMask);
	}
	public static void main(String[] args) {
		Y ytest = new Y();
	}
}
