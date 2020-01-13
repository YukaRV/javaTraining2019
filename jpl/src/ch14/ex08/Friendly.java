package ch14.ex08;

// 14.8
// Friendlyプログラムを試しなさい。使用しているシステムでどの程度の頻度で実際に
// デッドロックが発生しますか。yield呼び出しを追加したら、デッドロックの頻度を変更できますか。
// もし可能なら、この練習問題を1種類以上のシステムで試しなさい。
// 同期を削除することなくデッドロックの可能性を取り除いてみなさい。

// 手動で実行した場合: 10回中全てでデッドロックは生じなかった
// for文で100回回そうとした場合: 10回実行を押した全てにおいて、i<2でデッドロックが生じた。
// yieldをRunnable内に追加しても同様の結果であった。

// hugのsynchronizedを、hashCodeの小さい方から順にsynchronizedを呼び出すよう変更した。

public class Friendly extends Thread implements Comparable<Friendly> {
	private Friendly partner;
	private String name;

	public Friendly(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Friendly someone) {
		int diff = this.hashCode() - someone.hashCode();
		if (diff > 0) return 1;
		if (diff < 0) return -1;
		return 0;
	}

	public void hug() {
		Friendly a,b;
		if (compareTo(partner) > 0) {
			a = partner;
			b = this;
		} else {
			a = this;
			b = partner;
		}
		synchronized (a) {
			synchronized (b) {
				System.out.println(Thread.currentThread().getName() +
					" in " + name + ".hug() trying to invoke" +
					partner.name + ".hugBack()");
				partner.hugBack();
			}
		}
	}

	public synchronized void hugBack() {
		System.out.println(Thread.currentThread().getName() +
			" in " + name + ".hugBack()");
	}

	public void becomeFriend(Friendly partner) {
		this.partner = partner;
	}

	public static void main(String[] args) {
		final Friendly jareth = new Friendly("jareth");
		final Friendly cory = new Friendly("cory");

		jareth.becomeFriend(cory);
		cory.becomeFriend(jareth);

		for (int i = 0;i < 100;i++) {
				new Thread(new Runnable() {
					public void run() { jareth.hug(); }
				}, "Thread1").start();

				new Thread(new Runnable() {
					public void run() { cory.hug(); }
				}, "Thread2").start();
		}
	}
}
