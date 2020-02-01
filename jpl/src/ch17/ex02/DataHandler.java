package ch17.ex02;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;

// 17.2
// DataHandlerを修正してlastFileも弱く保存されるようにしなさい。

public class DataHandler {
	private WeakReference<File> lastFile;
	private WeakReference<byte[]> lastData;

	public static void main(String[] args){
		DataHandler dataHandler= new DataHandler();
		if (args.length > 0) {
			byte[] byteData = dataHandler.readFile(new File(args[0]));
		}
	}

	byte[] readFile(File file) {
		byte[] data;

		// データを記憶しているか調べる
		if (file.equals(lastFile.get())) {
			data = lastData.get();
			if (data != null)
				return data;
		}

		// 記憶していないので、読み込む
		data = readBytesFromFile(file);
		lastFile = new WeakReference<File>(file);
		lastData = new WeakReference<byte[]>(data);
		return data;
	}

	byte[] readBytesFromFile(File file) {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
}
