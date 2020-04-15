package interpret;

// 16.9
// 指定されたクラスの完全な宣言を表示するプログラムをリフレクションを使用して作成しなさい。
// ただし、インポート文、コメント、それに、初期化子、コンストラクタ、メソッドのコードは除外します。
// メンバー宣言は、ソ－スコードに書かれたように表示すべきです。
// 今まで説明してきたすべてのリフレクションクラスを使用する必要があるでしょう。
// 多くのリフレクションオブジェクトのtoStringメソッドは、欲しい情報を正しい形式で提供しませんので、
// 個々の情報を集めてまとめる必要があります。

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Declaration {
	public static void main(String[] args) {
		try {
//			String[] args2 = {"ch16.ex09.TestClass"};
//			args = args2;
			Class<?> c = Class.forName(args[0]);
			Declaration decl = new Declaration();
			System.out.println(c.getPackage());
			decl.printType(c, 0, basic);
			System.out.println("{");
			printAllMember(c);
			System.out.println("}");
		} catch (ClassNotFoundException e) {
			System.out.println("unknown class: " + args[0]);
		}
	}

	private static void printAllMember(Class<?> cls) {
		// currentの全メンバーを表示
		printMembers(cls.getDeclaredFields(), true);
		printMembers(cls.getDeclaredConstructors(), true);
		printMembers(cls.getDeclaredMethods(), true);
		// publicの(継承されたものを含む)全メンバーを表示
		printMembers(cls.getFields(), false);
		printMembers(cls.getConstructors(), false);
		printMembers(cls.getMethods(), false);
	}

	private static void printMembers(Member[] mems, boolean exceptPublic) {
		for (Member m: mems) {
			if (m.getDeclaringClass() == Object.class)
				continue;
			if (exceptPublic && isPublic(m))
				continue;
			printMemberAnnotations(m, " ");
			String decl = memberToString(m);
			System.out.print(" ");
			String memStr = strip(decl, "java.lang.");
			System.out.println(memStr);
		}
	}

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

	// strip(decl, "java.lang.")のとき
	// 名前の先頭の"java.lang."を取り除く
	static String strip(String str, String diffStr) {
		return str.replace(diffStr, "");
	}

	static boolean isPublic(Member mem) {
		return ((mem.getModifiers() & Modifier.PUBLIC) != 0);
	}

	static void printMemberAnnotations(Member mem, String indentation) {
		Annotation[] annos;
		if (mem instanceof Field) {
			annos = ((Field)mem).getAnnotations();
		} else if (mem instanceof Constructor<?>) {
			annos = ((Constructor<?>)mem).getAnnotations();
		} else if (mem instanceof Method) {
			annos = ((Method)mem).getAnnotations();
		} else {
			System.out.println("unknown type of member: " + mem.toString());
			return;
		}
		printAnnotations(annos,indentation);
	}
	static void printAnnotations(Annotation[] annos, String indentation) {
		for (Annotation anno: annos) {
			String[] annoStr = anno.toString().split("\\$");
			String annoName = annoStr[annoStr.length-1];
			System.out.println(indentation + "@"+annoName.substring(0,annoName.length()-2));
		}
	}
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
			System.out.print(" ");
		System.out.print(labels[kind] + " ");
		System.out.print(cls.getCanonicalName());

		// あれば、ジェネリック型パラメータを表示
		TypeVariable<?>[] params = cls.getTypeParameters();
		if (params.length > 0) {
			System.out.print('<');
			for (TypeVariable<?> param: params) {
				System.out.print(param.getName());
				System.out.print(", ");
			}
			System.out.println("\b\b>");
		}
		else
			System.out.println();

		// ネストした先を表示
		if (cls.isMemberClass()) {
			System.out.println(" nest in "+cls.getEnclosingClass());
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
