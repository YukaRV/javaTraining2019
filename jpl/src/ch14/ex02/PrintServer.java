package ch14.ex02;

import java.awt.PrintJob;

// 14.2
// 最初のバージョンのPrintServerを修正して、本文で述べたように、
// スレッドの識別によりコンストラクタで生成されたスレッドだけがrunを実行できるようにしなさい。



public class PrintServer implements Runnable {
	private final PrintQueue requests = new PrintQueue();
	Thread targetThread;
	public PrintServer() {
		targetThread = new Thread(this);
		targetThread.start();
	}

	public void print(PrintJob job) {
		requests.add(job);
	}

	@Override
	public void run() {
		if (Thread.currentThread() == targetThread)
			// 印刷の実際の処理を行う
			System.out.println("run: " + Thread.currentThread().getName());
	}
	public static void main(String[] args) {
		PrintServer ps = new PrintServer();
		ps.run();
	}
}