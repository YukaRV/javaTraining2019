package dc2_1;

// dc 2.1
// Swing の JFrame を使用して、時間を表示するデジタル時計(アナログ時計は不可)を作成してください。
// ・java.awt.Frame を使用する。
// ・Windows の普通のアプリケーションと同様にタイトルバーの「×」ボタンをクリックすると終了する。
// ・デジタル時計の描画は、paintComponent メソッド内で Graphics を使用して行う。
//   テキストラベルによる単なる表示は、不可。

public class Main {
	public static void main(String[] args) {
		DigitalClock dc = new DigitalClock();
		dc.set();
	}

}
