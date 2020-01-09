package ch14.ex04;

// 14.4
// 練習問題14.3を修正してstaticデータとstaticメソッドを使用するようにしなさい。



public class AdditionThread implements Runnable{
	int val = 0;
	Thread targetThread;

	public AdditionThread() {
		targetThread = new Thread(this);
		targetThread.start();
	}
	@Override
	public void run() {
		if (Thread.currentThread() == targetThread) {
			for (int i = 0;i < 1000;i++) {
			CalculatableNumber.add(1);
			}
		}
	}

	public static void main(String[] args) {
		new AdditionThread();
		new AdditionThread();
	}
}