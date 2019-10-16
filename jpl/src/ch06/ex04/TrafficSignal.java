package ch06.ex04;

import java.awt.Color;

// 6.4
// 132頁の練習問題6.1の信号機の色のenumを修正して、
// 各enum定数が適切なColorオブジェクトを持ち、そのオブジェクトをgetColorで取り出せるようにしなさい。

enum TrafficSignal {
	BLUE(Color.BLUE),
	YELLOW(Color.YELLOW),
	RED(Color.RED);

	Color color;
	TrafficSignal(int r, int g, int b) { color = new Color(r,g,b); }
	TrafficSignal(Color c) { color = c; }
	Color getColor() { return color; }
}
