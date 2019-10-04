package ch03.ex10;

// (第2章の問題からの)LinkedListクラスをCloneableにして、値の複製ではなく、
// 元のリストと同じ値を参照している新たなリストを返すcloneメソッドを書きなさい。
// つまり、1つのリストに対する変更は、他方のリストには影響しないが、
// リストが参照しているオブジェクトに対する変更は、他方のリストから見えます。

public class LinkedList implements Cloneable{
	private Object obj;
	private LinkedList nextObj;
	public void set(Object obj) {
		this.obj = obj;
	}
	public void setNext(LinkedList list) {
		nextObj = list;
	}
	public void setNext(Object obj) {
		nextObj = new LinkedList();
		nextObj.set(obj);
	}
	public Object get() {
		return obj;
	}
	public LinkedList next() {
		return nextObj;
	}
	public int size() {
		int count = 0;
		LinkedList iter = this;
		while (iter != null) {
			count++;
			iter = iter.next();
		}
		return count;
	}

	@Override
	public String toString() {
		String txt = "[Object]\n";
		txt += get()+"\n";
		txt += "[nextObj]\n";
		if (nextObj != null)
			txt += nextObj.get();
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
		vs.set(car1);
		vs.setNext(bike1);
		vs.next().setNext(bicycle1);

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