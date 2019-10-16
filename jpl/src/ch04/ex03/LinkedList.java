package ch04.ex03;
//4.3
//前の練習問題のLinkedListクラスは、インタフェースであるべきですか。
//どうあるべきか決める前に、実装クラスとインタフェースクラスを使用して書き直しなさい。
//* LinkedList定義: Object型のフィールドと、次のLinkedList要素への参照を持つ

//インタフェースであるべき
//理由: そもそも、名前と性質が一致していないため、分離すべきである。
//      前のLinkedListはいわゆるIteratorである。
//      その上で、単純な内容であることからArrayの実装等List以外にも
//      使える可能性があるため、インタフェースであるべき。

public class LinkedList implements Node, Cloneable{
	// void add();
	// nodeclass private Node head;
	// nodeclass private Node tail;
	private Object curObj;
	private LinkedList nextNode;

	private LinkedList headNode;// tailIdx-1までは要素がある
	private LinkedList tailNode;// tailIdx-1までは要素がある

	public LinkedList() {
		this(null);
	}
	public LinkedList(Object obj) {
		curObj = obj;
		headNode = tailNode = this;
		nextNode = null;
	}

	public final void add(Object obj) {
		add(new LinkedList(obj));
	}
	public final void add(LinkedList list) {
		if (headNode.get() == null) {
			curObj = list.curObj;
			nextNode = list.nextNode;
			headNode = tailNode = this;
		} else {
			tailNode.nextNode = list;
			tailNode = tailNode.nextNode;
		}
	}

	public final void set(Object obj) {
		curObj = obj;
	}

	@Override
	public final Object get() {
		return curObj;
	}
	@Override
	public final LinkedList next() {
		return nextNode;
	}

	public final int size() {
		LinkedList iter = headNode;
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
	public LinkedList clone() {
		LinkedList list;
		try {
			list = (LinkedList)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
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

		LinkedList vs = new LinkedList();
		vs.add(car1);
		vs.add(bike1);
		vs.add(bicycle1);

		LinkedList vs2 = vs.clone();

		// 参照が同じなのでvs2.get()も変わる
		Vehicle car2 = (Vehicle)(vs.get());
		car2.setOwner("Alice");
		LinkedList iter = vs;

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