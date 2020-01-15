package ch14.ex09;

// 14.9
// スレッドグループを引数にとり、そのグループ内のスレッドとスレッドグループの階層を
// 定期的に表示するスレッドを開始するメソッドを書きなさい。
// そのメソッドを、様々なグループ内でいくつかの短命なスレッドを生成するプログラムでテストしなさい。

public class ThreadGroupController {
	int interval = 1000;

	public void printThreadGroup(ThreadGroup threadGroup) {
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					printHierarchy(threadGroup);
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "Thread1").start();
	}

	// 階層の一番上
	public void printHierarchy(ThreadGroup threadGroup) {
		System.out.println(threadGroup.getParent().getName());
		System.out.println(" "+threadGroup.getName());
		printCurDepth(threadGroup, 2);
	}

	private void printCurDepth(ThreadGroup threadGroup, int depth) {
		String indent = "";
		for (int i = 0;i < depth;i++) indent += " ";

		int threadCount = threadGroup.activeCount();
		Thread[] threadList = new Thread[threadCount];
		threadGroup.enumerate(threadList, false);
		for(Thread thread: threadList) {
			if(thread != null) {
				System.out.print(indent);
				System.out.println(thread.getName());
			}
		}

		int groupCount = threadGroup.activeGroupCount();
		ThreadGroup[] groupList = new ThreadGroup[groupCount];
		threadGroup.enumerate(groupList, false);
		for(ThreadGroup group: groupList) {
			System.out.print(indent);
			System.out.println(group.getName());
			printCurDepth(group, depth + 1);
		}
	}
}
