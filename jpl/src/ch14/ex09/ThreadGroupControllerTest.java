package ch14.ex09;

// 14.9
// スレッドグループを引数にとり、そのグループ内のスレッドとスレッドグループの階層を
// 定期的に表示するスレッドを開始するメソッドを書きなさい。
// そのメソッドを、様々なグループ内でいくつかの短命なスレッドを生成するプログラムでテストしなさい。

public class ThreadGroupControllerTest {
	public static void main(String[] args) {
		ThreadGroupControllerTest tgct = new ThreadGroupControllerTest();
		ThreadGroup parent = new ThreadGroup("group1");
		for (int i = 0;i < 4;i++) {
			new Thread(parent, tgct.new ShortLivedThread()).start();
		}
		ThreadGroup child = new ThreadGroup(parent, "group2");
		for (int i = 0;i < 4;i++) {
			new Thread(child, tgct.new ShortLivedThread()).start();
		}
		ThreadGroup child2 = new ThreadGroup(parent, "group3");
		for (int i = 0;i < 5;i++) {
			new Thread(child2, tgct.new ShortLivedThread()).start();
		}
		ThreadGroupController tdController = new ThreadGroupController();
		tdController.printThreadGroup(parent);
	}

	class ShortLivedThread implements Runnable{

		public void run() {
			try {
				System.out.println(Thread.currentThread().getName()+" start");
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName()+" finish");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
