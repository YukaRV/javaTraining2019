package ch01.ex02;

public class HelloWorld {
	// 1.2 エラー調査
	public static void main(String[] args) {
		System.out.println("[1]");
		System.out.println("System.out.println('Hello, world!');");
		String msg = "HelloWorld.java:4: エラー: 文字リテラルが閉じられていません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"                                   ^\r\n" +
				"		HelloWorld.java:4: エラー: 文ではありません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"                                     ^\r\n" +
				"		HelloWorld.java:4: エラー: ';'がありません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"                                         ^\r\n" +
				"		HelloWorld.java:4: エラー: 文ではありません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"                                           ^\r\n" +
				"		HelloWorld.java:4: エラー: ';'がありません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"                                                ^\r\n" +
				"		HelloWorld.java:4: エラー: 文字リテラルが閉じられていません\r\n" +
				"                System.out.println('Hello, world!');\r\n" +
				"												 ^";
		System.out.println(msg+"\n\n");


		System.out.println("[2]");
		System.out.println("System.out.println(Hello, world!);");
		msg = "HelloWorld.java:5: エラー: ')'がありません\r\n" +
				"                System.out.println(Hello, world!);\r\n" +
				"                                               ^";
		System.out.println(msg+"\n\n");


		System.out.println("[3]");
		System.out.println("System.out.println(\"Hello, world!\")");
		msg = "HelloWorld.java:6: エラー: ';'がありません\r\n" +
				"                System.out.println(\"Hello, world!\")\r\n" +
				"                                                   ^";
		System.out.println(msg);
	}
}
