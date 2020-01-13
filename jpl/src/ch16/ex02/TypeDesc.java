package ch16.ex02;

// 16.2
// 指定された型がネストした型かを示し、ネストした型ならどの型の中にネストしているかを示すように
// TypeDescを修正しなさい。

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeDesc {
	public static void main(String[] args) {
		String[] args2 = {"ch16.ex02.SingleLinkQueue"};
		args = args2;
		TypeDesc desc = new TypeDesc();
		for (String name: args) {
			try {
				Class<?> startClass = Class.forName(name);
				SingleLinkQueue testParentClass = new SingleLinkQueue();
				SingleLinkQueue.Cell testMemberClass = testParentClass.new Cell();
				desc.printType(testMemberClass.getClass(), 0, basic);// Class.forNameでCellを通すとエラーになるため
			} catch (ClassNotFoundException e) {
				System.err.println(e);	// report the error
			}
		}
	}

	// デフォルトで標準出力に表示する
	private java.io.PrintStream out = System.out;

	// 型名にラベル付けするprintType()で使用される
	private static String[]
			basic	 = { "class",	"interface",
						 "enum",	"annotation"},
			supercl = { "extends",	"implements" },
			iFace	 = { null,		"extends" };

	private void printType(
			Type type, int depth, String[] labels) {
		if (type == null) {	// 再起呼び出し停止: スーパータイプが存在しない
			return;
		}

		// TypeをClassオブジェクトに変換する
		Class<?> cls = null;
		if (type instanceof Class<?>)
			cls = (Class<?>)type;
		else if (type instanceof ParameterizedType)
			cls = (Class<?>)((ParameterizedType)type).getRawType();
		else
			throw new Error("Unexpected non-class type");

		// この型を表示
		int kind = cls.isAnnotation() ? 3 :
			cls.isEnum() ? 2 :
			cls.isInterface() ? 1 : 0;
		Type genericSuperclass = cls.getGenericSuperclass();
		if (genericSuperclass == null && kind == 0)
			return;
		for (int i = 0;i < depth;i++)
			out.print(" ");
		out.print(labels[kind] + " ");
		out.print(cls.getCanonicalName());

		// あれば、ジェネリック型パラメータを表示
		TypeVariable<?>[] params = cls.getTypeParameters();
		if (params.length > 0) {
			out.print('<');
			for (TypeVariable<?> param: params) {
				out.print(param.getName());
				out.print(", ");
			}
			out.println("\b\b>");
		}
		else
			out.println();

		// ネストした先を表示
		if (cls.isMemberClass()) {
			out.println(" nest in "+cls.getEnclosingClass());
		}

		// このクラスが実装しているすべてのインタフェースを表示
		Type[] interfaces = cls.getGenericInterfaces();
		for (Type iface: interfaces) {
			printType(iface, depth+1, cls.isInterface() ? iFace : supercl);
		}
		// スーパークラスに対して再帰
		printType(genericSuperclass, depth+1, supercl);
	}

	public class NestTest {

	}
}