package interpret;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InterpretDialog implements ActionListener,KeyListener,ItemListener{
	Interpreter itpr;
    ExceptionPanel exception;
	Class<?> root;
	private Object val;

	private JDialog frame;
	private JRadioButton[] radio;

	private int radioNum;
	private JPanel radioPanel;
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
	public InterpretDialog(Interpreter itpr) {
		this(itpr, null);
	}
	public InterpretDialog(Interpreter itpr, ExceptionPanel exception) {
		this.itpr = itpr;
		this.exception = exception;
	}
	private void init(Class<?> root) {
		frame = new JDialog(new JFrame(),true);
		text = new JTextField(20);
		text.addKeyListener(this);
		this.root = root;
		setRadio();
		radioNum = 0;
		radioPanel = getRadioPanel();
		refleshInputAllPanel();
		inputPanel = getInputPanel();
		okBtn = new JButton("ok");
		okPanel = getOkPanel();
		frame.setSize(800,600);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(radioPanel,BorderLayout.NORTH);
		frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
		frame.getContentPane().add(okPanel, BorderLayout.SOUTH);
	}
	public void selectObject(Class<?> root) {
		try {
			init(root);
	        frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (Exception e) {
			printError(e);
		}
	}
	private void setValue() {
		switch (radioNum) {
		case 0:
			try {
				val = TypeUtil.of(text.getText(),root);
			} catch (Exception e) {
				printError(e);
			}
			break;
		case 1:
			val = itpr.getVariable(String.valueOf(selector.getSelectedItem()));
			break;
		case 2:
			val = itpr.executeConstructor((Constructor<?>)selector.getSelectedItem(), variable);
			break;
		}
	}
	public Object getObject() {
		System.out.println(val);
		return val;
	}
	private void setRadio() {
		ButtonGroup radioGroup = new ButtonGroup();
		radio = new JRadioButton[3];
		radio[0] = new JRadioButton("value",true);
		radio[1] = new JRadioButton("variable");
		radio[2] = new JRadioButton("Component");
		for (int i = 0;i < radio.length;i++) {
			radio[i].addActionListener(this);
			radioGroup.add(radio[i]);
		}
	}
	private JPanel getRadioPanel() {
		JPanel p = new JPanel();
		for (int i = 0;i < radio.length;i++) {
			p.add(radio[i]);
		}
		return p;
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
		switch (radioNum) {
		case 0:
			break;
		case 1:
			HashMap<String, Object> res = itpr.getVariables();
			selectList = res.keySet().toArray();
			// 選択肢
			selector = new JComboBox<Object>(refine(selectList,text.getText()));
			selector.addItemListener(this);
			p.add(selector);
			break;
		case 2:
			selectList = itpr.getConstructors(root);
			// 選択肢
			selector = new JComboBox<Object>(refine(selectList,text.getText()));
			selector.addItemListener(this);
			p.add(selector);
			break;
		}
		return p;
	}

	private JPanel getInputPanel_variable() {
		JPanel p = new JPanel();
		switch (radioNum) {
		case 0:
		case 1:
			break;
		case 2:
			// 引数
			if (selector.getSelectedItem() instanceof Constructor<?>) {
				Constructor<?> cstr = (Constructor<?>)selector.getSelectedItem();
				variableBtns = new JButton[cstr.getParameterCount()];
				variable = new Object[cstr.getParameterCount()];
				Class<?>[] types = cstr.getParameterTypes();
				for (int i = 0;i < variableBtns.length;i++) {
					variableBtns[i] = new JButton(types[i].toString());
					variableBtns[i].addActionListener(this);
					p.add(variableBtns[i]);
				}
			}
			break;
		}
		return p;
	}
	private JPanel getOkPanel() {
		JPanel p = new JPanel();
		okBtn.addActionListener(this);
		p.add(okBtn);
		return p;
	}
	/**
	 * テキストの絞り込み
	 * TODO: utilClass作って移動
	 * @param orgList
	 * @param txt
	 * @return
	 */
	public static Object[] refine(Object[] orgList, String txt) {
		ArrayList<Object> newList = new ArrayList<>();
		for (Object obj: orgList) {
			if (obj.toString().toLowerCase().contains(txt.toLowerCase()))
				newList.add(obj);
		}
		return newList.toArray();
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
	private void refleshInputSelectorPanel() {
		inputPanel.remove(inputPanel_selector);
		inputPanel_selector = getInputPanel_selector();
		refleshInputVariablePanel();
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
						Constructor<?> cstr = (Constructor<?>)selector.getSelectedItem();
						Class<?> cls = cstr.getParameterTypes()[i];
						if (cls.isArray()) {
							ArrayDialog d = new ArrayDialog(frame,itpr,exception);
							d.selectObject(cls);
							variable[i] = d.getObject();
							System.out.println();
						}else {
							InterpretDialog d = new InterpretDialog(itpr,exception);
							d.selectObject(cls);
							variable[i] = d.getObject();
						}
					}
				}
			}
		} else if (srcObj instanceof JRadioButton) {
			for (int i = 0;i < radio.length;i++) {
				if (srcObj == radio[i]) {
					radioNum = i;
					refleshInputAllPanel();
					refleshInputPanel();
					break;
				}
			}
		} else if (srcObj instanceof JTextField) {
			System.out.println("text field");
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
			refleshInputSelectorPanel();
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
	private void printError(Exception e) {
		if (exception == null) {
			e.printStackTrace();
		} else {
			exception.printStackTrace(e);
		}
	}
}