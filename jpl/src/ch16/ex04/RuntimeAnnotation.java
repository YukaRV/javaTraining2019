package ch16.ex04;

import java.lang.annotation.Annotation;

// 16.4
// 指定された型に適用されていて得られるすべてのアノテーションを表示するプログラムを書きなさい。
// （リテンションポリシーがRUNTIMEを持つアノテーションだけが得られます。）

public class RuntimeAnnotation {
	public static void main(String[] args) {
		String[] args2 = {"ch16.ex04.ReflectAnnotationExample"};
		args = args2;
		Class<?> reflectAnnotation = ReflectAnnotationExample.AnnotationClassTest.class;
		printAnnotations(reflectAnnotation.getAnnotations());
	}

	static void printAnnotations(Annotation[] annos) {
		for (Annotation anno: annos) {
			System.out.println(anno.toString());
		}
	}
}
