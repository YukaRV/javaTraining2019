package interpret;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExceptionPanel extends JPanel{
	JLabel label = new JLabel("Exception");
	String text;
	public ExceptionPanel() {
		setBackground(Color.LIGHT_GRAY);
		text = "";
		add(label);
	}
	public void resetLabel() {
		text = "";
		label.setText("<html>"+text+"</html>");
	}
	public <T> void setLabel(T obj) {
		text += "<br/>" + obj.toString();
		label.setText("<html>"+text+"</html>");
	}
	public <T extends Exception> void printStackTrace(T e) {
		resetLabel();
		setLabel(e.getClass().getName() + ": " + e.getLocalizedMessage());
		StackTraceElement[] stes = e.getStackTrace();
		for (StackTraceElement ste:stes) {
			setLabel("　　"+ste);
		}
	}
}
