package ch13.ex04;

import java.util.ArrayList;

// 13.4
// "type value"形式の行を持つ入力文字列を読み込むプログラムを書きなさい。
// typeは、ラッパークラス(Boolean, Characterなど)の1つであり、
// valueはその型のコンストラクタが変換できる文字列です。
// 各行に対して、指定された値を持つ、指定された型のオブジェクトを生成して
// ArrayListに追加しなさい(ArrayListについては、512頁の｢ArrayList｣を参照)。
// 全ての行が読み込まれたら、最終結果を表示しなさい。各行は、
// 単純に改行文字'\n'で終了していると想定しなさい。

public class ReadString<E> {
	public static ArrayList<Object> readTnV(String lines) {
		ArrayList<Object> list = new ArrayList<>();
		String[] lineArray = lines.split("\n");
		for (String type_value: lineArray) {
			String[] tv = type_value.split(" ");
			switch (tv[0]) {
			case "Boolean":
				list.add(Boolean.parseBoolean(tv[1]));
				break;
			case "Character":
				list.add(tv[1].charAt(0));
				break;
			case "Byte":
				list.add(Byte.parseByte(tv[1]));
				break;
			case "Short":
				list.add(Short.parseShort(tv[1]));
				break;
			case "Integer":
				list.add(Integer.parseInt(tv[1]));
				break;
			case "Long":
				list.add(Long.parseLong(tv[1]));
				break;
			case "Float":
				list.add(Float.parseFloat(tv[1]));
				break;
			case "Double":
				list.add(Double.parseDouble(tv[1]));
				break;
			}
		}
		return list;
	}

	public static void main(String[] args) {
		String lines = "Integer 1\n"
						+ "Boolean true\n"
						+ "Double 1";
		ArrayList<Object> result = readTnV(lines);
		for (Object obj : result) {
			System.out.println(obj);
		}
	}
}