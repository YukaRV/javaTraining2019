package ch20.ex09;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// 20.9
// 1つかそれ以上のパス名を渡されて、それが表すファイルについて得られる
// すべての情報を表示するメソッドを書きなさい。

public class FileInformation {
	public static void main(String[] args) {
		try {
			URI[] uri = {new URI("file:/D:/workspace/jpl/imageFiles/ch20ex09.txt")};
			printFileInfo(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public  static void printFileInfo(URI[] uris) {
		for (URI uri: uris) {
			File file = new File(uri);
			System.out.println("getName() = " + file.getName());
			System.out.println("getPath() = " + file.getPath());
			System.out.println("getAbsolutePath() = " + file.getAbsolutePath());
			try {
				System.out.println("getCanonicalPath() = " + file.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("getParent() = " + file.getParent());
			System.out.println("lastModified() = " + file.lastModified());
			System.out.println("length() = " + file.length());
			System.out.println("list() = " + file.list());
		}
	}
}
