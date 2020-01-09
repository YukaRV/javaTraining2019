package ch14.ex03;


// 14.3
// 現在の値を保持し、その値に加算して新たな値を表示するメソッドを持つオブジェクトのクラスを作成しなさい。
// そのオブジェクトを生成し、複数のスレッドを生成して、
// 各スレッドからその加算メソッドを繰り返し呼び出すプログラムを作成しなさい。
// 加算の結果が失われないようにそのクラスを作成しなさい。


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