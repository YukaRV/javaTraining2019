package ch16.ex01;

// 16.1
// Objectクラスに関しては何も表示しないようにTypeDescを修正しなさい。
// すべては、究極的にObjectクラスを継承しているので、Objectクラスに関する表示は冗長です。
// Object型に対するClassオブジェクトの参照を使用しなさい。

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeDesc {
	public static void main(String[] args) {
		TypeDesc desc = new TypeDesc();
		for (String name: args) {
			try {
				Class<?> startClass = Class.forName(name);
				desc.printType(startClass, 0, basic);
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

		// このクラスが実装しているすべてのインタフェースを表示
		Type[] interfaces = cls.getGenericInterfaces();
		for (Type iface: interfaces) {
			printType(iface, depth+1, cls.isInterface() ? iFace : supercl);
		}
		// スーパークラスに対して再帰
		printType(genericSuperclass, depth+1, supercl);
	}
}
