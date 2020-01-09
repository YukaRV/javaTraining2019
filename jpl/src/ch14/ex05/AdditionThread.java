package ch14.ex05;

import ch14.ex03.CalculatableNumber;

// 14.5
// 練習問題14.4のコードを修正して、
// staticの同期されたメソッドを使用しないでスレッドが安全に値を保持できるようにしなさい。




public class AdditionThread implements Runnable{
	int val = 0;
	CalculatableNumber cn;
	Thread targetThread;

	public AdditionThread(CalculatableNumber cn) {
		this.cn = cn;
		targetThread = new Thread(this);
		targetThread.start();
	}

	@Override
	public void run() {
		if (Thread.currentThread() == targetThread) {
			for (int i = 0;i < 1000;i++) {
				cn.add(1);
			}
		}
	}

	public static void main(String[] args) {
		CalculatableNumber cn = new CalculatableNumber();
		new AdditionThread(cn);
		new AdditionThread(cn);
	}
}