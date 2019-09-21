package dc1_1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.JOptionPane;


public class DigitalClock extends Frame implements Runnable,WindowListener,ComponentListener{
	int width = 500, height = 200;

	/**
	 * 時間を保持
	 */
	private Calendar date;
	private String hhmmss;
	private String yyyymmdd;


    public static void main(String[] args) {
    	JOptionPane.showMessageDialog(null, "ディレクトリ構成にsrcはいらないぞ");
    	DigitalClock dc = new DigitalClock();
		Thread thread = new Thread(dc);
		thread.start();
    }

	public DigitalClock() {
		init();
		setSize(width, height);
		addWindowListener(this);
		addComponentListener(this);
		setVisible(true);
	}

	/**
	 * 初期値の設定
	 */
	public void init() {
		// get now time
		date = Calendar.getInstance();
		// 1,2. フォント、サイズの指定
		Font font = new Font(Font.MONOSPACED,Font.BOLD,50);
		setFont(font);
		// 3. 文字色の指定
		setForeground(Color.DARK_GRAY);
		// 4. 時計の背景色の指定
		setBackground(Color.WHITE);
	}

	/**
	 * スレッドの実行処理
	 */
	@Override
	public void run() {
		while (true) {
			// get now time
			date = Calendar.getInstance();
			repaint();
			try {
				// wait 1/10 second
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	/**
	 * 描画メソッド
	 */
	public void paint(Graphics g) {
		hhmmss = timeToString();
		yyyymmdd = dateToString();
		int fontSize = getFont().getSize();
		drawStringCenter(g,hhmmss,   width/2, height/2);
		drawStringCenter(g,yyyymmdd, width/2, height/2+fontSize);//ascent使った方がいいの？
	}

	// accessor

	// utility
	public String timeToString() {
		String h = convertDigitString(date.get( Calendar.HOUR_OF_DAY ),2);
		String m = convertDigitString(date.get( Calendar.MINUTE ),2);
		String s = convertDigitString(date.get( Calendar.SECOND ),2);
		return h+":"+m+":"+s;
	}
	public String dateToString() {
		String yyyy = convertDigitString(date.get( Calendar.YEAR ),4);
		String mm = convertDigitString(date.get( Calendar.MONTH ),2);
		String dd = convertDigitString(date.get( Calendar.DAY_OF_MONTH ),2);
		return yyyy+"/"+mm+"/"+dd;
	}
	public String convertDigitString(int num,int digit) {
		// digitを越えた桁を削除
		num %= (int)( Math.pow(10,digit));
		String format = "%0"+digit+"d";
		return String.format(format, num);
	}

	public static void drawStringCenter(Graphics g,String text,int x,int y){
		FontMetrics fontMet = g.getFontMetrics();
		Rectangle textRect = fontMet.getStringBounds(text, g).getBounds();
		int cx = x-textRect.width/2;
		int cy = y-textRect.height/2+fontMet.getMaxAscent();

        g.drawString(text, cx, cy);
	}

	// WindowListener
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

	// ComponentListener
	@Override
	public void componentResized(ComponentEvent e) {
		width = getWidth();
		height = getHeight();
	}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}

}
