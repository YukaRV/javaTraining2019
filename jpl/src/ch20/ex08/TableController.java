package ch20.ex08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// 20.8
// %%で始まる行で分割されているエントリーを持つファイルを読み込み、
// 各エントリーの開始位置を持つテーブルファイルを作成するプログラムを書きなさい。
// そして、そのテーブルを使用してランダムにエントリーを表示するプログラムを作成しなさい。
// (579頁の｢MathとStrictMath｣で説明されているMath.randomメソッドを参照)

public class TableController {
	public static void main(String[] args) {
		TableController tc = new TableController();
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile("imageFiles/ch20ex08raf.txt", "r");
			String idxFileName = "imageFiles/ch20ex08index.txt";
			tc.setTableFile(raf,idxFileName);
			tc.printEntry(raf,idxFileName);
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setTableFile(RandomAccessFile raf, String fileName) throws IOException, FileNotFoundException {
		FileOutputStream fout = new FileOutputStream(fileName);
		DataOutputStream out = new DataOutputStream(fout);
		int c;
		ArrayList<Long> idx = new ArrayList<>();
		while((c = raf.read()) != -1) {
			if (c == '%' && raf.read() == '%')
				idx.add(raf.getFilePointer());
		}
		out.writeInt(idx.size());
		for (long x:idx) {
			out.writeLong(x);
		}
		out.close();
	}
	public void printEntry(RandomAccessFile raf, String fileName) throws IOException, FileNotFoundException {
		FileInputStream fin = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fin);
		int len = in.readInt();
		long[] idxList = new long[len+1];
		for (int i = 0;i < len;i++)
			idxList[i] = in.readLong();
		int idx = (int)(Math.random()*len);
		idxList[len] = raf.length()+2;
		raf.seek(idxList[idx]);
		for (int i = 0;i < (int)(idxList[idx+1]-idxList[idx]-2);i++) {
			System.out.print((char)raf.read());
		}
	}
}
