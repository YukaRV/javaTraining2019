package ch11.ex01;

// 11.1
// 練習問題2.2で始めたLinkedListクラスを見直して、ジェネリッククラスとして書き直しなさい。


public class LinkedList<E> implements Cloneable{
	private Object curObj;
	private LinkedList<E> nextNode;

	public LinkedList() {
		this(null);
	}
	public LinkedList(Object obj) {
		curObj = obj;
		nextNode = null;
	}

	public final void add(Object obj) {
		add(new LinkedList<E>(obj));
	}
	public final void add(LinkedList<E> list) {
		LinkedList<E> iter = this;
		if (curObj == null) {
			curObj = list.curObj;
			nextNode = list.nextNode;
			return;
		}
		while (iter.nextNode != null) iter = iter.nextNode;
		iter.nextNode = list;
	}

	public final void set(Object obj) {
		curObj = obj;
	}

	public final Object get() {
		return curObj;
	}
	public final LinkedList<E> next() {
		return nextNode;
	}

	public final int size() {
		LinkedList<E> iter = this;
		int length = 0;
		while(iter != null) {
			length++;
			iter = iter.next();
		}
		return length;
	}

	@Override
	public String toString() {
		String txt = "[Object]\n";
		txt += get()+"\n";
		txt += "[nextObj]\n";
		if (next() != null)
			txt += next().get();
		else
			txt += "null";
		return txt;
	}

	@Override
	public LinkedList<E> clone() {
		LinkedList<E> list =  new LinkedList<E>();
		LinkedList<E> iter = this;
		int size = iter.size();
		for (int i = 0;i < size;i++) {
			list.add(iter.curObj);
			iter = iter.next();
		}
		return list;
	}

	public static void main(String[] args) {
		Vehicle car1 = new Vehicle();
		car1.setSpeed(100);
		car1.setOwner("Taro");

		Vehicle bike1 = new Vehicle();
		car1.setDegree(45);

		Vehicle bicycle1 = new Vehicle();
		bicycle1.setSpeed(50);
		bicycle1.setDegree(-45);
		bicycle1.setOwner("Hanako");

		LinkedList<Vehicle> vs = new LinkedList<Vehicle>();
		vs.add(car1);
		vs.add(bike1);
		vs.add(bicycle1);

		LinkedList<Vehicle> vs2 = vs.clone();

		// 参照が同じなのでvs2.get()も変わる
		Vehicle car2 = (Vehicle)(vs.get());
		car2.setOwner("Alice");
		LinkedList<Vehicle> iter = vs;

		while (iter != null) {
			System.out.println(iter);
			System.out.println();
			iter = iter.next();
		}
		iter = vs2;

		while (iter != null) {
			System.out.println(iter);
			System.out.println();
			iter = iter.next();
		}

		System.out.println("---------------------------\n");

		// set作るとprevがつながらないから、使うならprev作って双方向リストにしろ
		// 参照先を変えてもvs2.get()は変わらない
		vs.set(bicycle1);

		iter = vs;
		while (iter != null) {
			System.out.println(iter);
			System.out.println();
			iter = iter.next();
		}

		iter = vs2;

		while (iter != null) {
			System.out.println(iter);
			System.out.println();
			iter = iter.next();
		}
	}
}