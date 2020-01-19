/*
 * Copyright (C) 2012, 2013 RICOH Co., Ltd. All rights reserved.
 * Copyright (C) 2019 Yoshiki Shibata. All rights reserved.
 */
package ch14.ex10;

import java.util.concurrent.ArrayBlockingQueue;

// TODO 追加課題14.10
// 第14章の追加問題として練習問題10を解いてもらいます。練習問題は、以下のリポジトリの
// ThreadPool.java と ThreadPoolTest.java の二つのソースファイルです。
// https://github.com/YoshikiShibata/jpltest/tree/master/jpl/ch14/ex10
// ファイル内のpacage宣言をjpl.ch14.ex10からch14.10へ修正して、
// 各人のjpl/src/ch14/ex10とjpl/test/ch14/ex10ディレクトリの下に、ThreadPool.java とThreadPoolTest.java を配置してださい。
// 練習問題の内容は以下の通りです。
// • ThreadPoolTest.javaは完成したテストコードであり修正しない
// • ThreadPoolTest.java内のテストがすべて合格するように ThreadPool.java を完成させる

/**
 * Simple Thread Pool class.
 *
 * This class can be used to dispatch an Runnable object to
 * be exectued by a thread.<br><br>
 *
 * [Instruction]
 * <ul>
 *  <li> Implement one constructor and three methods. </li>
 *  <li> Don't forget to write a Test program to test this class. </li>
 *  <li> Pay attention to @throws tags in the javadoc. </li>
 *  <li> If needed, you can put "synchronized" keyword to methods. </li>
 *  <li> All classes for implementation must be private inside this class. </li>
 *  <li> Don't use java.util.concurrent package. </li>
 *  <li> Don't use {@link java.lang.Thread#interrupt}  method to stop a thread</li>
 *  </ul>
 *
 *  @author Yoshiki Shibata
 */
public class ThreadPool {
	int queueSize;
	ArrayBlockingQueue<Runnable> queue;
	int numberOfThreads;
	ThreadWrapper[] threads;
	/**
	 * Constructs ThreadPool.
	 *
	 * @param queueSize the max size of queue
	 * @param numberOfThreads the number of threads in this pool.
	 *
	 * @throws IllegalArgumentException if either queueSize or numberOfThreads
	 *         is less than 1
	 */
	public ThreadPool(int queueSize, int numberOfThreads) {
		if (queueSize <= 0)
			throw new IllegalArgumentException("queue size: "+queueSize);
		if (numberOfThreads <= 0)
			throw new IllegalArgumentException("number of threads: "+numberOfThreads);
		this.queueSize = queueSize;
		queue = new ArrayBlockingQueue<>(queueSize);
		this.numberOfThreads = numberOfThreads;
		threads = new ThreadWrapper[numberOfThreads];
		for (int i = 0;i < numberOfThreads;i++) {
			threads[i] = new ThreadWrapper(this);
		}
	}

	/**
	 * Starts threads.
	 *
	 * @throws IllegalStateException if threads has been already started.
	 */
	public void start() {
		for (int i = 0;i < numberOfThreads;i++) {
			synchronized (threads[i]) {
				if (threads[i].isAlive())
					throw new IllegalStateException("threads has been already started.");
				threads[i].start();
			}
		}
	}

	/**
	 * Stop all threads gracefully and wait for their terminations.
	 * All requests dispatched before this method is invoked must complete
	 * and this method also will wait for their completion.
	 *
	 * @throws IllegalStateException if threads has not been started.
	 */
	public void stop() {
		for (int i = 0;i < numberOfThreads;i++) {
			if (!threads[i].isAlive())
				throw new IllegalStateException("threads has not been started.");
			// TODO interrupt使ったらエラー出すテストになっているらしいのでうまく避けるか別の方法考えるか
			// nterrupt使わないでねって最初言ってたから使わないのが正攻法？
			ThreadWrapper thread = threads[i];
			thread.setContinue(false);
			synchronized (thread) {
				thread.notifyAll();
			}
		}
	}

	/**
	 * Executes the specified Runnable object, using a thread in the pool.
	 * run() method will be invoked in the thread. If the queue is full, then
	 * this method invocation will be blocked until the queue is not full.
	 *
	 * @param runnable Runnable object whose run() method will be invoked.
	 *
	 * @throws NullPointerException if runnable is null.
	 * @throws IllegalStateException if this pool has not been started yet.
	 */
	public void dispatch(Runnable runnable) {
		if (runnable == null)
			throw new NullPointerException("runnable is null");
		if (!threads[0].isAlive())
			throw new IllegalStateException("threads has not been started.");
		synchronized (this) {
			if (queue.remainingCapacity() > 0) {
				queue.add(runnable);
				notifyAll();
			}
	//		else
	//			System.out.println("queue.length < number of task");
		}
		// 柴田さん｢CPU使わないコードにしてください｣
	}

	class ThreadWrapper extends Thread{
		ThreadPool monitor;
		private boolean isContinue = true;
		public ThreadWrapper(ThreadPool monitor) {
			super();
			this.monitor = monitor;
		}
		public void run() {
			Runnable runnable = null;
			while(isContinue) {
				if (runnable != null) {
					runnable.run();
					runnable = null;
				}
				synchronized (monitor) {
					try {
						monitor.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (queue.size() > 0) {
						runnable = queue.remove();
					}
				}
			}
		}
		public void setContinue(boolean isContinue) {
			this.isContinue = isContinue;
		}
	}
}