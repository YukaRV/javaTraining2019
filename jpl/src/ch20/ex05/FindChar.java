package ch20.ex05;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

// 20.5
// 指定されたファイルを読み込んで、指定された単語を検索するプログラムを作成しなさい。
// 単語が発見された全ての行を、行の前に行番号を付けて表示しなさい。


public class FindChar {
	public static void main(String[] args) throws IOException {
		if (args.length != 2)
			throw new IllegalArgumentException("need char and file");
		String match = args[0];
		FileReader fileIn = new FileReader(args[1]);
		LineNumberReader in = new LineNumberReader(fileIn);
		String str;
		Pattern p = Pattern.compile(match);
		boolean find = false;
		while ((str = in.readLine()) != null) {
			if (p.matcher(str).find()) {
				find = true;
				System.out.println(in.getLineNumber() + ":" + str);
			}
		}
		if (!find)
			System.out.println(match + " not found");
	}


}
