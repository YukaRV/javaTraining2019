package _interpret;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditorPanel extends JPanel{
	JLabel label = new JLabel("Editor");
	String text;
	public EditorPanel() {
		setBackground(Color.WHITE);
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
	public void print(HashMap<String, Object> variableList) {
		resetLabel();
		for(Iterator<String> iterator = variableList.keySet().iterator(); iterator.hasNext(); ) {
			String key = iterator.next();
			setLabel(variableList.get(key).getClass()+" "+ key);
		}
	}
}
