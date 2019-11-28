package dc1_3;

import java.awt.Graphics;

// 必要なもの
// 0~9の描画(描き方は自由)

/**
 * デジタル時計の数字を好きに描画するためのインタフェース
 * @author p000527465
 *
 */
public interface DrawableClock{
	/**
	 *  1文字描画
	 * @param g
	 * @param n
	 */
	void drawClock(Graphics g, int x, int y);


	interface Chr {
		char getChar();
	}
	enum Symbol implements Chr{
		COLON(':'),
		SLASH('/'),
		SPACE(' ');

		char chr;
		Symbol(char c) {
			chr = c;
		}
		@Override
		public char getChar() {
			return chr;
		}
		public static Symbol getSymbol(char c) {
			switch(c) {
			case ':':
				return COLON;
			case '/':
				return SLASH;
			case ' ':
				return SPACE;
			default:
				return null;
			}
		}
	};
	enum Num implements Chr{
		ZERO('0'),
		ONE('1'),
		TWO('2'),
		THREE('3'),
		FOUR('4'),
		FIVE('5'),
		SIX('6'),
		SEVEN('7'),
		EIGHT('8'),
		NINE('9');

		char chr;
		Num(char c) {
			chr = c;
		}
		@Override
		public char getChar() {
			return chr;
		}
		public static Num getNum(char n) {
			if (n < '0' || n > '9') {
				return null;
			}
			return getNum((int)(n - '0'));
		}
		public static Num getNum(int n) {
			if (n < 0 || n > 9) {
				throw new IllegalArgumentException("0~9の整数を入力してください");
			}
			switch (n) {
			case 0:
				return ZERO;
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			case 4:
				return FOUR;
			case 5:
				return FIVE;
			case 6:
				return SIX;
			case 7:
				return SEVEN;
			case 8:
				return EIGHT;
			case 9:
				return NINE;
			}
			return null;
		}
	};
}
