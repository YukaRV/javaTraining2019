package ch14.ex06;

// 14.6
// 15秒間隔でメッセージを表示する別のスレッドを持ち、実行開始からの経過時間を表示するプログラムを作成しなさい。
// メッセージ表示スレッドは、時間表示スレッドから1秒経過するごとに通知されるようにしなさい。
// 時間表示スレッドを修正することなく、7秒間隔で異なるメッセージを表示する別のスレッドを追加しなさい。

public class PrintTimeMessage {

	public static void main(String[] args) {
		PrintTimeMessage ptm = new PrintTimeMessage();
		PrintTimeThread ptt = ptm.new PrintTimeThread();
		ptm.new PrintMessageThread(ptt, "15test", 15);
		ptm.new PrintMessageThread(ptt, "07test", 7);
	}

	class PrintTimeThread implements Runnable{
		Thread targetThread;
		long startTime;
		int interval = 100;
		int prevSec = 0;

		public PrintTimeThread() {
			targetThread = new Thread(this);
			startTime = System.currentTimeMillis();
			targetThread.start();
		}
		@Override
		public void run() {
			if (Thread.currentThread() == targetThread) {
				printSecond();
			}
		}
		public void printSecond() {
			try {
				while(true) {
					synchronized (this) {
						long curTime = System.currentTimeMillis();
						if ((curTime-startTime)/1000 >= prevSec+1) {
							prevSec++;
							System.out.println(prevSec+" second");
							notifyAll();
						}
							wait(interval);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	class PrintMessageThread implements Runnable{
		Thread targetThread;
		long startTime;
		String message;
		int messageInterval;
		int prevSec = 0;
		PrintTimeThread monitor;

		public PrintMessageThread() {
			this(null, "no message",15);
		}
		public PrintMessageThread(PrintTimeThread monitor, String message, int messageInterval) {
			this.monitor = monitor;
			targetThread = new Thread(this);
			this.message = message;
			this.messageInterval = messageInterval;
			startTime = System.currentTimeMillis();
			targetThread.start();
		}
		@Override
		public void run() {
			if (Thread.currentThread() == targetThread) {
				printMessage();
			}
		}
		public void printMessage() {
			try {
				while(true) {
					synchronized (monitor) {
						monitor.wait();
						long curTime = System.currentTimeMillis();
						if ((curTime-startTime)/1000 >= prevSec+messageInterval) {
							prevSec += messageInterval;
							System.out.println(message);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
