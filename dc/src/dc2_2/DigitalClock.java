package dc2_2;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DigitalClock extends JFrame implements Runnable, ActionListener{
	ClockPanel mainPanel;

	public DigitalClock() {
		init();
	}
	/**
	 * Threadのセット
	 */
	public void set() {
		Thread thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		while (true) {
			mainPanel.updateTime();
			try {
				// wait 1/10 second
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 初期設定
	 */
	public void init() {
		setVisible(true);
		setMenu();
		setClock();
		setFrameSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setLocationRelativeTo(null);
	}
	/**
	 * 時計をこのFrameに追加する
	 */
	public void setClock() {
		mainPanel = new ClockPanel(this);
	    ComponentProperty.setProperty(mainPanel, new ComponentProperty());
		getContentPane().add(mainPanel);
	}

	public void setMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("デザイン" );
		JMenuItem menuItem = new JMenuItem("プロパティ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
	/**
	 *フレームサイズを設定する
	 */
	public void setFrameSize() {
		// 要素から必要なサイズをとってくる
		// 今回はmainPanelのみなのでそこのフォントサイズ
		String t = LocalDateTime.MAX.format(mainPanel.getDatetimeFormat());
		Graphics g = mainPanel.getGraphics();
	    FontMetrics fontMet = g.getFontMetrics();
		Rectangle textRect = fontMet.getStringBounds(t, g).getBounds();
		int paddingTop = 5;
		int paddingLeft = 10;
		getContentPane().setPreferredSize(
				new Dimension(textRect.width+paddingLeft*2, textRect.height+paddingTop*2)
		);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			// Menuにはプロパティしかないので
			SettingDialog sd = new SettingDialog(this, ComponentProperty.getProperty(mainPanel));
			ComponentProperty.setProperty(mainPanel, sd.get());
		}
	}
}
