package interpret;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MethodDialog implements ActionListener, KeyListener, ItemListener{
	Interpreter itpr;
    ExceptionPanel exception;
	Object rootObj;
	private Method mtd;

	private JPanel inputPanel;
	private JTextField text;
	private JPanel inputPanel_value;
	private Object[] selectList;
	private JComboBox<Object> selector;
	private JPanel inputPanel_selector;
	private JButton[] variableBtns;
	private Object[] variable;
	private JPanel inputPanel_variable;
	private JButton okBtn;
	private JPanel okPanel;

	private JDialog frame;
	public MethodDialog(Interpreter itpr) {
		this(itpr, null);
	}
	public MethodDialog(Interpreter itpr,ExceptionPanel exception) {
		this.itpr = itpr;
		this.exception = exception;
	}
	private void init(Object rootObj) {
		frame = new JDialog(new JFrame(),true);
		text = new JTextField(20);
		text.addKeyListener(this);
		this.rootObj = rootObj;
		refleshInputAllPanel();
		inputPanel = getInputPanel();
		okBtn = new JButton("ok");
		okPanel = getOkPanel();
		frame.setSize(800,600);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
		frame.getContentPane().add(okPanel, BorderLayout.SOUTH);
	}
	public void selectObject(Object rootObj) {
		init(rootObj);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	private void setValue() {
		mtd = (Method)selector.getSelectedItem();
	}
	public Object getObject() {
		Object obj = itpr.executeMethod(rootObj, mtd, variable);
		System.out.println(obj);
		return mtd;
	}
	private JPanel getInputPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(inputPanel_value);
		p.add(inputPanel_selector);
		p.add(inputPanel_variable);
		return p;
	}
	private JPanel getInputPanel_value() {
		JPanel p = new JPanel();
		p.add(text);
		return p;
	}
	private JPanel getInputPanel_selector() {
		JPanel p = new JPanel();
		selectList = itpr.getMethods(rootObj);
		// 選択肢
		selector = new JComboBox<Object>(InterpretDialog.refine(selectList,text.getText()));
		selector.addItemListener(this);
		p.add(selector);
		return p;
	}
	private JPanel getInputPanel_variable() {
		JPanel p = new JPanel();
		// 引数
		if (selector.getSelectedItem() instanceof Method) {
			Method mtd = (Method)selector.getSelectedItem();
			variableBtns = new JButton[mtd.getParameterCount()];
			variable = new Object[mtd.getParameterCount()];
			Class<?>[] types = mtd.getParameterTypes();
			for (int i = 0;i < variableBtns.length;i++) {
				variableBtns[i] = new JButton(types[i].toString());
				variableBtns[i].addActionListener(this);
				p.add(variableBtns[i]);
			}
		}
		return p;
	}
	private JPanel getOkPanel() {
		JPanel p = new JPanel();
		okBtn.addActionListener(this);
		p.add(okBtn);
		return p;
	}
	private void refleshInputPanel() {
		frame.getContentPane().remove(inputPanel);
		inputPanel = getInputPanel();
		frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
		frame.getContentPane().revalidate();
	}
	private void refleshInputAllPanel() {
		if (inputPanel != null) {
			inputPanel.removeAll();
		}
		inputPanel_value = getInputPanel_value();
		inputPanel_selector = getInputPanel_selector();
		inputPanel_variable = getInputPanel_variable();
	}
	private void refleshInputVariablePanel() {
		inputPanel.remove(inputPanel_variable);
		inputPanel_variable = getInputPanel_variable();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object srcObj = e.getSource();
		if (srcObj instanceof JButton) {
			if (srcObj == okBtn) {
				setValue();
				frame.setVisible(false);
			}
			else {
				for (int i = 0;i < variableBtns.length;i++) {
					JButton btn = variableBtns[i];
					if (srcObj == btn) {
						InterpretDialog d = new InterpretDialog(itpr,exception);
						Method cstr = (Method)selector.getSelectedItem();
						Class<?> cls = cstr.getParameterTypes()[i];
						d.selectObject(cls);
						variable[i] = d.getObject();
						System.out.println();
					}
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("enter");
			refleshInputAllPanel();
			refleshInputPanel();
			text.requestFocus();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == selector) {
			refleshInputVariablePanel();
			refleshInputPanel();
		}
	}
}
