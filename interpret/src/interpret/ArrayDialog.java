package interpret;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ArrayDialog extends JDialog implements ActionListener, ChangeListener{
	Interpreter itpr;
    ExceptionPanel exception;

	JPanel panel;
	private int width = 400;
	private int height = 250;
	JSpinner spinner;
	JPanel elemPanel;
	JButton[] elemButton;
	Object[] elem;
	Class<?> cls;
	JButton okBtn;


	public static void main(String[] args) {
		char[] t = {'a','b','c'};
		System.out.println(new String(t));
		ArrayDialog ad = new ArrayDialog(null, null, null);
	}
	public ArrayDialog(Window owner, Interpreter itpr, ExceptionPanel exception) {
		super(owner, "プロパティ", ModalityType.DOCUMENT_MODAL);
		this.itpr = itpr;
		this.exception = exception;
		setSize(new Dimension(width,height));
	}

	public void selectObject(Class<?> root) {
		try {
			init(root);
	        setLocationRelativeTo(null);
			setVisible(true);
		} catch (Exception e) {
			printError(e);
		}
	}
	public Object getObject() {
		System.out.println(elem);
		try {
			return TypeUtil.castArray(elem, cls.getComponentType());
		} catch (Exception e) {
			printError(e);
			return null;
		}
	}

	public void init(Class<?> root) {
		this.cls = root;
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(panel);
		panel.add(getSpinnerPanel(), BorderLayout.NORTH);
		elemPanel = getElemPanel(0);
		panel.add(elemPanel, BorderLayout.CENTER);
		JPanel okPanel = new JPanel();
		okBtn = new JButton("ok");
		okBtn.addActionListener(this);
		okPanel.add(okBtn);
		panel.add(okPanel, BorderLayout.SOUTH);
		add(pane);
	}
	public JPanel getSpinnerPanel() {
		JPanel spinnerPanel = new JPanel();
		spinner = new JSpinner();
		spinner.addChangeListener(this);
		spinnerPanel.add(spinner);
		return spinnerPanel;
	}
	public JPanel getElemPanel(int length) {
		JPanel elemPanel = new JPanel();
		elemPanel.setLayout(new BoxLayout(elemPanel, BoxLayout.Y_AXIS));
		elemButton = new JButton[length];
		elem = new Object[length];
		for (int i = 0;i < length;i++) {
			elemButton[i] = getElemButton("element "+i);
			elemPanel.add(elemButton[i]);
		}
		return elemPanel;
	}
	public JButton getElemButton(String title) {
		JButton btn = new JButton();
		btn = new JButton(title);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton)e.getSource();
			if (button.equals(okBtn)) {
				setVisible(false);
			}
			for (int i = 0;i < elemButton.length;i++) {
				if (button.equals(elemButton[i])) {
					System.out.println(i);
					InterpretDialog d = new InterpretDialog(itpr,exception);
					System.out.println(cls.getComponentType());
					d.selectObject(cls.getComponentType());
					elem[i] = d.getObject();
				}
			}
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSpinner) {
			JSpinner spinner = (JSpinner)e.getSource();
			if ((int)spinner.getValue() < 0)
				spinner.setValue(0);
			panel.remove(elemPanel);
			elemPanel = getElemPanel((int)spinner.getValue());
			panel.add(elemPanel, BorderLayout.CENTER);
			revalidate();
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
