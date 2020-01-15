package ch16.ex05;

// 16.5
// 個々のメンバーに対して得られるアノテーション情報を含むようにClassContentsを拡張しなさい。

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class ClassContents {
	public static void main(String[] args) {
		try {
			String[] args2 = {"ch16.ex04.ReflectAnnotationExample"};
			args = args2;
			Class<?> c = Class.forName(args[0]);
			System.out.println(c);
			// currentの全メンバーを表示
			printMembers(c.getDeclaredFields(), true);
			printMembers(c.getDeclaredConstructors(), true);
			printMembers(c.getDeclaredMethods(), true);
			// publicの(継承されたものを含む)全メンバーを表示
			printMembers(c.getFields(), false);
			printMembers(c.getConstructors(), false);
			printMembers(c.getMethods(), false);
		} catch (ClassNotFoundException e) {
			System.out.println("unknown class: " + args[0]);
		}
	}


	private static void printMembers(Member[] mems, boolean exceptPublic) {
		for (Member m: mems) {
			if (m.getDeclaringClass() == Object.class)
				continue;
			if (exceptPublic && isPublic(m))
				continue;
			printMemberAnnotations(m, " ");
			String decl = m.toString();
			System.out.print(" ");
			System.out.println(strip(decl, "java.lang."));
		}
	}

	// strip(decl, "java.lang.")のとき
	// 名前の先頭の"java.lang."を取り除く
	static String strip(String str, String diffStr) {
		return str.replace(diffStr, "");
	}

	static boolean isPublic(Member mem) {
		String memStr = mem.toString();
		int i = memStr.lastIndexOf(" ");
		String modifier = memStr.substring(0,i);
		if (modifier.contains("public")) {
			return true;
		}
		return false;
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
			System.out.println(indentation + anno.toString());
		}
	}
}
