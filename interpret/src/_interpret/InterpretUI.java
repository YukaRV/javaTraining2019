package _interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// TODO 16.09など使って正確なデータをとってくる
// 16.6
// 要求された型のオブジェクトを生成し、ユーザがそのオブジェクトのフィールドを調べて、
// フィールドを修正できるInterpretプログラムを作成しなさい。
//
// 16.7
// オブジェクトに対してメソッドを呼び出すようにInterpretプログラムを修正しなさい。
// 戻り値やスローされた例外を適切に表示するようにしなさい。
//
// 16.8 TODO
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
//		console, exceptionでの表示は可能
//		TODO 可視範囲がおかしいのをなおすこと、Editorで表示するものを追加すること
//• TODO java.awt.FrameのsetVisible()、setTitle()、setSize()、setBackground()を呼び出すデモができること
//• コンストラクタやメソッドの呼び出しで発生した例外も正しく表示されること
//• TODO 参照型の配列を生成して、各要素に個別に参照を代入できること
//• TODO private final のインスタンスフィールドの書き換えもできること
//• 自分自身を起動できること
//Interpret の課題は、講師から OK がでるまで何度も再提出(ただし、3 回が限度)してもらい確認
//を行います。OK が出ない場合には、受講資格を失うこともありますので、注意してください。なお、
//3回でOKになればよいですが、1回目は6割以上はできている必要があります。


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

	public InterpretUI() {
		init();
	}
	public void start() {
		setMenu();
		setLayout();
		mainFrame.add(new JPanel());
		interpreter = new Interpreter(console,exception);
	}
	public void setVisible(boolean b) {
		mainFrame.setVisible(b);
	}
	public void init() {
		mainFrame = new JFrame();
		dim = new Dimension(900,600);
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
        contentPane.setLayout(new GridLayout(1,1));
        editor = new EditorPanel();
        console = new ConsolePanel();
        JScrollPane consoleScroll = new JScrollPane(console);
        exception = new ExceptionPanel();
        JScrollPane exceptionScroll = new JScrollPane(exception);
        contentPane.add(editor);
        contentPane.add(consoleScroll);
        contentPane.add(exceptionScroll);
        mainFrame.add(contentPane);
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
		// 存在をチェック
		Object obj = interpreter.getNewObject(objectClass);
		if (obj == null) {
			return;
		}

		String objectName = JOptionPane.showInputDialog("name of variable");
		interpreter.setVariable(objectName, obj);
		editor.print(interpreter.getVariables());
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
		interpreter.updateField(interpreter.getVariable(variableName), field, updateValue);
	}
	public void executeMethod() {
		// 変数を選択
		String variableName = JOptionPane.showInputDialog("name of variable");
		if (!interpreter.hasVariable(variableName)) {
			return;
		}

		// メソッドを選択
		Method[] methods = interpreter.getMethods(variableName);
		Method method = (Method)JOptionPane.showInputDialog(null, "", "name of method", JOptionPane.PLAIN_MESSAGE, null, methods, methods[0]);

		// 引数の指定
		// TODO: 型指定で複数引数
		Type[] param = method.getGenericParameterTypes();
		String[] argsStr = new String[param.length];
		for (int i = 0;i < param.length;i++) {
			argsStr[i] = JOptionPane.showInputDialog(i+" argument: "+param[i].getTypeName());
		}
		System.out.println(interpreter.executeMethod(interpreter.getVariable(variableName), method, argsStr));
	}

	static class InterpretMenu extends MenuBar implements ActionListener {
		InterpretUI ui;
		enum MenuLabel {
			ADD_OBJECT("オブジェクトの追加"),
			SHOW_OBJECT("オブジェクトの表示"),
			UPDATE_FIELD("フィールドの編集"),
			EXECUTE_METHOD("メソッドの実行");
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
				} else if (itemLabel == MenuLabel.UPDATE_FIELD.getLabel()) {
					ui.updateField();
				} else if (itemLabel == MenuLabel.EXECUTE_METHOD.getLabel()) {
					ui.executeMethod();
				} else {
					JOptionPane.showMessageDialog(null, "未実装");
				}
			}
		}

	}

	// 16.XXからコピー。TODO ちゃんと書きなおす
	static String memberToString(Member mem) {
		String memStr = getModifiersStr(mem);
		if (mem instanceof Field) {
			Field field = (Field)mem;
			memStr += field.getName();
			return memStr;
		} else if (mem instanceof Constructor<?>) {
			return mem.toString();
		} else if (mem instanceof Method) {
			Method method = (Method) mem;
			Type rtn = method.getGenericReturnType();
			memStr += rtn.toString() + " ";
			memStr +=  method.getName() + "(";
			Type[] param = method.getGenericParameterTypes();
			for (int i = 0;i < param.length;i++) {
				memStr += param[i].getTypeName();
				if (i != param.length-1)
					memStr += ",";
			}
			memStr += ")";
			Type[] exc = method.getGenericExceptionTypes();
			return memStr;
		} else {
			System.out.println("unknown type of member: " + mem.toString());
			return "";
		}
	}
	static String getModifiersStr(Member mem) {
		String str = "";
		int modis = mem.getModifiers();
		if (Modifier.isPublic(modis)) str += "public ";
		if (Modifier.isProtected(modis)) str += "protected ";
		if (Modifier.isPrivate(modis)) str += "private ";
		if (Modifier.isFinal(modis)) str += "final ";
		if (Modifier.isStatic(modis)) str += "static ";
		if (Modifier.isSynchronized(modis)) str += "synchronized ";
		if (Modifier.isAbstract(modis)) str += "abstract ";
		if (Modifier.isInterface(modis)) str += "interface ";
		return str;
	}
}
