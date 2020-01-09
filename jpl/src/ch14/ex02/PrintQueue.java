package ch14.ex02;

import java.awt.PrintJob;

// P309



public class PrintQueue {
	private SingleLinkQueue<PrintJob> queue = new SingleLinkQueue<PrintJob>();

	public synchronized void add(PrintJob j) {
		queue.add(j);
		notifyAll();	// 待っているスレッドに知らせる: プリントジョブが追加された
	}

	public synchronized PrintJob remove() throws InterruptedException{
		while (queue.size() == 0)
			wait();		// プリントジョブを待つ
		return queue.remove();
	}
}