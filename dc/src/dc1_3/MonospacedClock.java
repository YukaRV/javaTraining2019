package dc1_3;

import java.awt.Graphics;
import java.time.LocalDateTime;

// 必要なもの
// 0~9の描画(描き方は自由)

/**
 * デジタル時計の数字をオリジナルで描画する
 * @author p000527465
 *
 */
public class MonospacedClock implements DrawableClock{
	private LocalDateTime datetime;
	private int fontWidth = 25,fontHeight = 35;
	private int charSpacing = 5;
	private final double sizeRate = (double)fontHeight/fontWidth, spaceRate = (double)charSpacing/fontWidth;
	private final String DATE_FORMAT="yyyy/mm/dd";// フォーマット
	private final String TIME_FORMAT="hh:mm:ss";// フォーマット

	private void setFontSize(Graphics g) {
		fontWidth = g.getFont().getSize()/2;
		fontHeight = (int)Math.round(fontWidth*sizeRate);
		charSpacing = (int)Math.round(fontWidth*spaceRate);
	}
	public void drawClockCenter(Graphics g, int cx, int cy) {
		setFontSize(g);
		setFontSize(g);
		int timeWidth = TIME_FORMAT.length()*(fontWidth+charSpacing)-charSpacing;
		int dateWidth = DATE_FORMAT.length()*(fontWidth+charSpacing)-charSpacing;
		int height = fontHeight;// timeが1行の場合
		datetime = LocalDateTime.now();
		drawTime(g,  cx-timeWidth/2, cy-height*3/2);
		drawDate(g, cx-dateWidth/2, cy+height*1/2);
	}
	public void drawClock(Graphics g, int x, int y) {
		setFontSize(g);
		datetime = LocalDateTime.now();
		drawTime(g, x, y);
		drawDate(g, x, y+fontHeight*2);
	}

	public final void drawTime(Graphics g, int x, int y) {
		int dx = fontWidth+charSpacing;
		int i = 0,
			count = 0,
			length = TIME_FORMAT.length();
		char c;
		while(true) {
			c = TIME_FORMAT.charAt(i);
			// 連続する値をまとめる(yyyyとかmmとか)
			if ((i+count)+1 < length && c == TIME_FORMAT.charAt((i+count)+1)) {
				count++;
				continue;
			}
			// ここから描画
			switch (c) {
			case 'h':
				drawNumber(g, convertDigitString(datetime.getHour(),count+1), x+dx*i, y);
				break;
			case 'm':
				drawNumber(g, convertDigitString(datetime.getMinute(),count+1), x+dx*i, y);
				break;
			case 's':
				drawNumber(g, convertDigitString(datetime.getSecond(),count+1), x+dx*i, y);
				break;
			default:
				drawSymbol(g, Symbol.getSymbol(c), x+dx*i, y);
				break;
			}
			i += count+1;
			count = 0;
			if (i >= length) break;
		}
	}

	public final void drawDate(Graphics g, int x, int y) {
		int dx = fontWidth+charSpacing;
		int i = 0,
			count = 0,
			length = DATE_FORMAT.length();
		char c;
		while(true) {
			c = DATE_FORMAT.charAt(i);
			// 連続する値をまとめる(yyyyとかmmとか)
			if ((i+count)+1 < length && c == DATE_FORMAT.charAt((i+count)+1)) {
				count++;
				continue;
			}
			// ここから描画
			switch (c) {
			case 'y':
				drawNumber(g, convertDigitString(datetime.getYear(),count+1), x+dx*i, y);
				break;
			case 'm':
				drawNumber(g, convertDigitString(datetime.getMonthValue(),count+1), x+dx*i, y);
				break;
			case 'd':
				drawNumber(g, convertDigitString(datetime.getDayOfMonth(),count+1), x+dx*i, y);
				break;
			default:
				drawSymbol(g, Symbol.getSymbol(c), x+dx*i, y);
				break;
			}
			i += count+1;
			count = 0;
			if (i >= length) break;
		}
	}

	/**
	 * 数値numをdigit桁の文字列にして返す
	 * (2019,2)	-> 19
	 * (3,4)	-> 0003
	 * @param num 桁を修正したい整数
	 * @param digit 修正後の桁数
	 * @return 修正したnum
	 */
	private String convertDigitString(int num,int digit) {
		// digitを越えた桁を削除
		num %= (int)( Math.pow(10,digit));
		String format = "%0"+digit+"d";
		return String.format(format, num);
	}

	/**
	 * Symbolに含まれる記号を描画する
	 * @param g
	 * @param sbl
	 * @param x
	 * @param y
	 */
	public final void drawSymbol(Graphics g, Symbol sbl, int x, int y) {
		switch (sbl) {
		case COLON:
			int radius = fontWidth/10;
			g.fillRect(x+fontWidth/2-radius, y+fontHeight*1/3-radius, radius*2, radius*2);
			g.fillRect(x+fontWidth/2-radius, y+fontHeight*2/3-radius, radius*2, radius*2);
			break;
		case SLASH:
			g.drawLine(x+fontWidth, y, x, y+fontHeight);
			break;
		case SPACE:
			break;
		}
	}

	/**
	 * 自然数を描画する
	 * @param g
	 * @param numStr
	 * @param x
	 * @param y
	 */
	public final void drawNumber(Graphics g, String numStr, int x, int y) {
		if (!numStr.matches("[0-9]*")) {
			return;
		}
		int digit = numStr.length();
		for (int i = 0;i < digit;i++) {
			char n = numStr.charAt(i);
			drawSingleDigitNumber(g, Num.getNum(n), x+(fontWidth+charSpacing)*i, y);
		}
	}

	/**
	 * 0～9の自然数を描画する
	 * @param g
	 * @param n
	 * @param x
	 * @param y
	 */
	public final void drawSingleDigitNumber(Graphics g, Num n, int x, int y) {
		SevenPin sp;
		switch (n) {
		case ZERO:
			//g.drawOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,true,true,true,true,true,false);
			sp.draw(g, x, y);
			break;
		case ONE:
			//g.fillRect(x+fontWidth/2-width/2,y,width,fontHeight);
			sp = new SevenPin(false,false,true,true,false,false,false);
			sp.draw(g, x, y);
			break;
		case TWO:
			//g.drawOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(false,true,true,false,true,true,true);
			sp.draw(g, x, y);
			break;
		case THREE:
			//g.fillOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(false,true,true,true,true,false,true);
			sp.draw(g, x, y);
			break;
		case FOUR:
			//g.drawOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,false,true,true,false,false,true);
			sp.draw(g, x, y);
			break;
		case FIVE:
			//g.fillOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,true,false,true,true,false,true);
			sp.draw(g, x, y);
			break;
		case SIX:
			//g.drawOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,true,false,true,true,true,true);
			sp.draw(g, x, y);
			break;
		case SEVEN:
			//g.fillOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,true,true,true,false,false,false);
			sp.draw(g, x, y);
			break;
		case EIGHT:
			//g.drawOval(x,y,fontWidth,fontHeight);
			sp = new SevenPin(true,true,true,true,true,true,true);
			sp.draw(g, x, y);
			break;
		case NINE:
			sp = new SevenPin(true,true,true,true,true,false,true);
			sp.draw(g, x, y);
			//g.fillOval(x,y,fontWidth,fontHeight);
			break;
		}
	}


	/**
	 * デジタルでよくある7本のピンで数字を描画する
	 *     1
	 *    ――
	 * 0 | 6  | 2
	 *    ――
	 * 5 |    | 3
	 *    ――
	 *     4
	 * @author p000527465
	 *
	 */
	private class SevenPin {
		private boolean[] pins = new boolean[7];
		SevenPin(boolean pin0,
					boolean pin1,
					boolean pin2,
					boolean pin3,
					boolean pin4,
					boolean pin5,
					boolean pin6) {
			boolean[] ps = {
					pin0,
					pin1,
					pin2,
					pin3,
					pin4,
					pin5,
					pin6
					};
			setPins(ps);
		}
		void setPins(boolean[] ps) {
			if (ps.length != pins.length) return;
			for (int i = 0;i < pins.length;i++) {
				pins[i] = ps[i];
			}
		}

		void draw(Graphics g, int x, int y) {
			if (pins.length != 7) return;
			if (pins[0]) {
				g.drawLine(x+fontWidth/10,y+fontHeight/10,x+fontWidth/10,y+fontHeight*4/10);
			}
			if (pins[1]) {
				g.drawLine(x+fontWidth/10,y+fontHeight/10,x+fontWidth*9/10,y+fontHeight/10);
			}
			if (pins[2]) {
				g.drawLine(x+fontWidth*9/10,y+fontHeight/10,x+fontWidth*9/10,y+fontHeight*4/10);
			}
			if (pins[3]) {
				g.drawLine(x+fontWidth*9/10,y+fontHeight*6/10,x+fontWidth*9/10,y+fontHeight*9/10);
			}
			if (pins[4]) {
				g.drawLine(x+fontWidth/10,y+fontHeight*9/10,x+fontWidth*9/10,y+fontHeight*9/10);
			}
			if (pins[5]) {
				g.drawLine(x+fontWidth/10,y+fontHeight*6/10,x+fontWidth/10,y+fontHeight*9/10);
			}
			if (pins[6]) {
				g.drawLine(x+fontWidth/10,y+fontHeight*5/10,x+fontWidth*9/10,y+fontHeight*5/10);
			}
		}
	}
}
