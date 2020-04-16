package dc2_1;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;

public class ClockPanel extends JPanel {
	/** 描画する時刻 */
	private LocalDateTime dateTime;
	/** 時刻の表示フォーマット */
	private DateTimeFormatter format;
	public ClockPanel() {
		dateTime = LocalDateTime.now();
		format = DateTimeFormatter.ofPattern("hh:mm:ss");
		setBackground(Color.black);
	}
	/**
	 * 画面に表示する日時情報の更新
	 */
	public void updateTime() {
		dateTime = LocalDateTime.now();
		repaint();
	}
	/**
	 * このパネルのフォーマッタを返す
	 * @return
	 */
	public DateTimeFormatter getDatetimeFormat() {
		return format;
	}
	@Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
		// 描画用文字列の更新
	    String timeStr = dateTime.format(format);
	    FontMetrics fontMet = g.getFontMetrics();
		Rectangle textRect = fontMet.getStringBounds(timeStr, g).getBounds();
	    int cx = getWidth()/2;
	    int cy = getHeight()/2-textRect.height/2+fontMet.getMaxAscent();
		g.drawString(timeStr, cx-textRect.width/2, cy);
	  }
}
