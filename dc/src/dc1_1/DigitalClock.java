package dc1_1;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;


public class DigitalClock extends Frame implements Runnable,WindowListener{
	int width = 500, height = 200;

	Calendar now;
	String h,m,s;
	String nowStr = "";
	public DigitalClock() {
		setSize(width, height);
		addWindowListener(this);
		setVisible(true);
	}

    public void paint(Graphics g)
    {
        g.drawString(nowStr,100,100);
    }

	@Override
	public void run() {
		while (true) {
			// get now time
			nowStr = timeToString();
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
	public String timeToString() {
		now = Calendar.getInstance();
		h = convertTwoDigitString(now.get( Calendar.HOUR_OF_DAY ));
		m = convertTwoDigitString(now.get( Calendar.MINUTE ));
		s = convertTwoDigitString(now.get( Calendar.SECOND ));
		return h+":"+m+":"+s;
	}
	public String convertTwoDigitString(int num) {
		return String.format("%02d", num);
	}

    public static void main(String[] args) {
    	DigitalClock dc = new DigitalClock();
		Thread thread = new Thread(dc);
		thread.start();
    }

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

}
