package ch14.ex09;

// TODO 14.9
// スレッドグループを引数にとり、そのグループ内のスレッドとスレッドグループの階層を
// 定期的に表示するスレッドを開始するメソッドを書きなさい。
// そのメソッドを、様々なグループ内でいくつかの短命なスレッドを生成するプログラムでテストしなさい。

public class ThreadGroupControllerTest {
	public static void main(String[] args) {
		ThreadGroup threadGroup = new ThreadGroup("group");
		ThreadGroupController tdController = new ThreadGroupController();
		tdController.printHierarchy(threadGroup);
	}

}
