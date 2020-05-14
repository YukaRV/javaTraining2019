package interpret;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// 16.6
// 要求された型のオブジェクトを生成し、ユーザがそのオブジェクトのフィールドを調べて、
// フィールドを修正できるInterpretプログラムを作成しなさい。
//
// 16.7
// オブジェクトに対してメソッドを呼び出すようにInterpretプログラムを修正しなさい。
// 戻り値やスローされた例外を適切に表示するようにしなさい。
//
// 16.8
// Interpretプログラムをさらに修正して、任意のクラスのコンストラクタをユーザが呼び出せるようにしなさい。
// その際にどんな例外も表示しなさい。また、オブジェクトの生成が成功したら、
// そのオブジェクトのメソッドをユーザが呼び出せるようにしなさい。
//
// 16.10 TODO
// Interpretをさらに修正して、ユーザが生成する配列の型とサイズを指定できて、その配列の要素を読み出したり設定したりできて、
// また、配列の要素として含まれているオブジェクトを指定して、そのオブジェクトのフィールドにアクセスしたりメソッドを呼び出したり
// できるようにしなさい。
//
// Interpret:
// 練習問題 16.6、16.7、16.8、16.10 をそれぞれ作成する代わりに、Interpret プログラムを1つ作成してもらいます。
// 練習問題で指定された操作ができることに加えて、以下のことも行ってもらいます。
//• GUI で作成する(AWT/Swing のどちらでも良い)
//• java.awt.FrameのsetVisible()、setTitle()、setSize()、setBackground()を呼び出すデモができること
//• コンストラクタやメソッドの呼び出しで発生した例外も正しく表示されること
//• TODO 参照型の配列を生成して、各要素に個別に参照を代入できること
//• 自分自身を起動できること

// 変数作るところで配列を選択できるようにする
// Dialogに配列が指定されたら値入れるところを配列の長さを指定できるようにして、縦にボタン生成する
// 時間があったら値のプレビュー

public class InterpretUI {
	JFrame mainFrame;
	Dimension dim;
	GridBagLayout gbl;

	EditorPanel editor;
    ConsolePanel console;
    ExceptionPanel exception;

	public int test=0;
	public String test2="";

	Interpreter interpreter;
    InterpretDialog itprDialog;

	public InterpretUI() {
		init();
	}
	public void start() {
		setMenu();
		setLayout();
		interpreter = new Interpreter(console,exception);
		itprDialog = new InterpretDialog(interpreter,exception);
	}
	public void setVisible(boolean b) {
		mainFrame.setVisible(b);
	}
	public void init() {
		mainFrame = new JFrame();
		dim = new Dimension(1200,600);
		mainFrame.setSize(dim);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
	}
	public void setMenu() {
		InterpretMenu menuBar = new InterpretMenu(this);
		mainFrame.setMenuBar(menuBar);
	}
	public void setLayout() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		editor = new EditorPanel();
		JScrollPane editorScroll = new JScrollPane(editor);
		console = new ConsolePanel();
		JScrollPane consoleScroll = new JScrollPane(console);
		exception = new ExceptionPanel();
		JScrollPane exceptionScroll = new JScrollPane(exception);
		Container panel = mainFrame.getContentPane();
		panel.setLayout(new GridLayout(1,3));
		panel.add(editorScroll);
		panel.add(consoleScroll);
		panel.add(exceptionScroll);
		mainFrame.setVisible(true);
	}

	protected void setGBC(GridBagConstraints gbc, int gridy, int gridx, int gridheight, int gridwidth, int anchor) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.anchor = anchor;
	}
	public void setNewObject() {
		String objectClass = JOptionPane.showInputDialog("name of class");
		if (objectClass == null) return;
		// 存在をチェック
		Class<?> cls = interpreter.getClass(objectClass);
		if (cls == null) return;
		InterpretDialog d = new InterpretDialog(interpreter,exception);
		d.selectObject(cls);
		Object obj = d.getObject();
		if (obj == null) {
			return;
		}

		String objectName = JOptionPane.showInputDialog("name of variable");
		interpreter.setVariable(objectName, obj);
		editor.print(interpreter.getVariables());
	}
	public void showObject() {
		itprDialog.selectObject(Color.class);
		itprDialog.getObject();
	}
	public void updateField() {
		String variableName = JOptionPane.showInputDialog("name of variable");
		Object obj = interpreter.getVariable(variableName);
		if (obj == null) {
			return;
		}
		// メソッドを選択
		Field[] fields = interpreter.getFields(variableName);
		Field field = (Field)JOptionPane.showInputDialog(null, "", "name of method", JOptionPane.PLAIN_MESSAGE, null, fields, fields[0]);

		String updateValue = JOptionPane.showInputDialog("update value");
		interpreter.updateField(obj, field, updateValue);
	}
	public void executeMethod() {
		// 変数を選択
		String variableName = JOptionPane.showInputDialog("name of variable");
		if (!interpreter.hasVariable(variableName)) {
			return;
		}

		// メソッドを選択
		MethodDialog d = new MethodDialog(interpreter,exception);
		d.selectObject(interpreter.getVariable(variableName));
		Object result = d.getObject();

		// 必要ならvariableにする
	}

	static class InterpretMenu extends MenuBar implements ActionListener {
		InterpretUI ui;
		enum MenuLabel {
			ADD_OBJECT("オブジェクトの追加"),
			SHOW_OBJECT("オブジェクトの表示"),
			UPDATE_FIELD("フィールドの編集"),
			EXECUTE_METHOD("メソッドの実行"),
			DEBUG("debug");
			String label;
			MenuLabel(String label) {
				this.label = label;
			}
			String getLabel() {
				return this.label;
			}
		}
		public InterpretMenu(InterpretUI interpret) {
			super();
			this.ui = interpret;
			add(prepMenu("オブジェクト",
					MenuLabel.ADD_OBJECT.label,
					MenuLabel.SHOW_OBJECT.label));
			add(prepMenu("編集",
					MenuLabel.UPDATE_FIELD.label,
					MenuLabel.EXECUTE_METHOD.label));
		}
		public Menu prepMenu(String menuLabel, String ...labels) {
			Menu menu = new Menu(menuLabel);
			for (String label:labels) {
				MenuItem menuItem = new MenuItem(label);
				menuItem.addActionListener(this);
				menu.add(menuItem);
			}
			return menu;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof MenuItem) {
				String itemLabel = ((MenuItem)e.getSource()).getLabel();
				if (itemLabel == MenuLabel.ADD_OBJECT.getLabel()) {
					ui.setNewObject();
				} else if(itemLabel == MenuLabel.SHOW_OBJECT.getLabel()) {
					// TODO: 修正
				} else if (itemLabel == MenuLabel.UPDATE_FIELD.getLabel()) {
					ui.updateField();
				} else if (itemLabel == MenuLabel.EXECUTE_METHOD.getLabel()) {
					ui.executeMethod();
				} else if(itemLabel == MenuLabel.DEBUG.getLabel()) {
					ui.showObject();
				} else {
					JOptionPane.showMessageDialog(null, "未実装");
				}
			}
		}

	}

}
