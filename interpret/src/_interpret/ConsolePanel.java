package _interpret;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsolePanel extends JPanel{
	JLabel label = new JLabel("Console");
	String text;
	public ConsolePanel() {
		setBackground(Color.GREEN);
		text = "";
		add(label);
	}
	public <T> void setLabel(T obj) {
		text += "<br/>" + obj.toString();
		label.setText("<html>"+text+"</html>");
	}
	public <T> void print(T obj) {
		setLabel(obj);
	}

}
