package ch14.ex09;

// TODO 14.9
// スレッドグループを引数にとり、そのグループ内のスレッドとスレッドグループの階層を
// 定期的に表示するスレッドを開始するメソッドを書きなさい。
// そのメソッドを、様々なグループ内でいくつかの短命なスレッドを生成するプログラムでテストしなさい。

public class ThreadGroupController {
	int interval = 1000;

	public void printHierarchy(ThreadGroup threadGroup) {
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					printThreadGroup(threadGroup);
					try {
						wait(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "Thread1").start();
	}
	public void printThreadGroup(ThreadGroup threadGroup) {

	}
}
