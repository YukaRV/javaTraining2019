package interpret;
import static org.junit.Assert.*;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

// 入力できてほしいテキスト
// int x = 300;
// int y = 200;
// System.out.println(x);
// java.awt.Frame f = new java.awt.Frame();
// f.setvisible(true);
// f.setTitle("testdayo");
// f.setSize(x,y);
// f.setBackground(Color.RED);

// UI的なノルマ
// 右辺入力ではコンストラクタ/基本型/変数を選べる
// コンストラクタ/変数の時は一覧が出る
// 上書きではConfirmDialogがでる

public class InterpretTest {
	private final int INT_TEST = 0;
	private final String STRING_TEST = "";
	public boolean printTest(String str, int str2) {
		System.out.println(str+str2);
		return true;
	}
	/**
	 * オブジェクトについての情報からオブジェクトを生成できるかのテスト
	 */
	@Test
	public void translateObjectTest() {
		// input: type(String), コンストラクタ(constructor, args)/基本型(String value)/変数(String variableName)
		// output: キャスト済みのObject
	}

	/**
	 * 変数を新規追加/上書きできるかのテスト<br/>
	 * input: objectName(string), RHS(Object)<br/>
	 * output: boolean<br/>
	 * RHS: コンストラクタ/基本型/変数<br/>
	 */
	@Test
	public void setVariableTest() {
		Interpreter interpreter = new Interpreter();
		String variableName = "test";
		// 定義時のtypeはRHSで判定
		// int
		setVariable_func(interpreter, variableName, 1234, 1234.0);

		// String
		setVariable_func(interpreter, variableName, "1234", 1234.0);

		// boolean
		setVariable_func(interpreter, variableName, true, 1234.0);
	}
	public void setVariable_func(Interpreter interpreter, String variableName, Object expected, Object notExpected) {
		interpreter.setVariable(variableName, expected);
		Object actual = interpreter.getVariable(variableName);
		assertEquals(expected,actual);
		assertNotEquals(notExpected,actual);
	}

	/**
	 * オブジェクトの変数を変更できるかのテスト<br/>
	 * input: variableName(String), fieldName(String), RHS(Object)<br/>
	 * output: boolean<br/>
	 * RHS: コンストラクタ/基本型/変数<br/>
	 */
	@Test
	public void updateFieldTest() {
		Interpreter interpreter = new Interpreter();
		String variableName = "iptTest";

		updateField_func(interpreter, variableName, "INT_TEST", 1234, 0);
		updateField_func(interpreter, variableName, "STRING_TEST", "1234", "");
	}
	public void updateField_func(Interpreter interpreter, String variableName, String fieldName, Object newFieldVal, Object oldFieldVal) {
		interpreter.setVariable(variableName, new InterpretTest());
		interpreter.updateField(variableName, fieldName, newFieldVal);

		Object actual;
		actual = interpreter.getFieldObject(variableName, fieldName);
		assertEquals(newFieldVal,actual);
		assertNotEquals(oldFieldVal,actual);
	}

	/**
	 * オブジェクトのメソッドを実行できるかのテスト<br/>
	 * input: variableName(String), methodName(String), RHSList(Object[])<br/>
	 * output: methodsReturn<br/>
	 * RHS: コンストラクタ/基本型/変数<br/>
	 */
	@Test
	public void executeMethodTest() {
		Interpreter interpreter = new Interpreter();
		String variableName = "iptTest";
		String methodName = "printTest";
		Class<?>[] argTypes = {String.class, int.class};
		Object[] args = {"print", 3};

		executeMethod_func(interpreter, variableName, methodName, args, argTypes, true);
	}
	public void executeMethod_func(Interpreter interpreter, String variableName, String methodName, Object[] args, Class<?>[] argTypes, Object expect) {
		interpreter.setVariable(variableName, new InterpretTest());
		Object variable = interpreter.getVariable(variableName);
		Method method = interpreter.getMethod(variable, methodName, argTypes);
		Object actual = interpreter.executeMethod(variable, method, args);

		assertEquals(expect,actual);
	}

	@Test
	public void executeConstructorTest() {
		Interpreter interpreter = new Interpreter();
		String variableName = "iptTest";
		Class<?>[] argTypes = {int.class,int.class,int.class};
		Object[] args = {200,100,0};

		executeConstructor_func(interpreter, variableName, args, argTypes, true);
	}
	public void executeConstructor_func(Interpreter interpreter, String variableName, Object[] args, Class<?>[] argTypes, Object expect) {
		interpreter.setVariable(variableName, new Color(0));
		Object variable = interpreter.getVariable(variableName);
		Constructor constructor = interpreter.getConstructor(variable, argTypes);
		Object actual = interpreter.executeConstructor(constructor, args);
		System.out.println("no constructor error");
		assertNotEquals(actual, null);
	}
}
