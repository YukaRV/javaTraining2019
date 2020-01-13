package ch16.ex03;

// 16.3
// すべての宣言されているメンバーとすべての継承されているpublicのメンバーに
// 関する情報を表示するようにClassContentsを修正しなさい。
// 同じものを2度表示しないようにしなさい。

import java.lang.reflect.Member;

public class ClassContents {
	public static void main(String[] args) {
		try {
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
}
