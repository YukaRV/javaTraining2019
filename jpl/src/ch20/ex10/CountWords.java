package ch20.ex10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.HashMap;

public class CountWords {
	public static void main(String[] args) {
		try {
			File file = new File("imageFiles/ch20ex10.txt");
			FileInputStream is = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(is);
			countEachWord(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void countEachWord(Reader reader) throws IOException{
		HashMap<String, Integer> map = new HashMap<>();
		StreamTokenizer in = new StreamTokenizer(reader);
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			if (in.ttype == StreamTokenizer.TT_WORD) {
				String val = in.sval;
				int count = map.containsKey(val) ? map.get(val) : 0;
				map.put(val,count+1);
			}
		}
		System.out.println(map);
	}
}
