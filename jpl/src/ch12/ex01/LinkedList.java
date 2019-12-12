package ch12.ex01;

// 12.1
// 以前の練習問題で作成したLinkedListクラスに対するObjectNotFoundExceptionクラスを作成しなさい。
// リストの中のオブジェクトを探すfindメソッドを追加して、
// 要求されたオブジェクトが含まれるLinkedListオブジェクトを返すか、
// オブジェクトがリスト中に発見されなければその例外をスローするようにしなさい。
// オブジェクトが発見されなかった時に、nullを返すより、例外をスローする方がなぜ好ましいですか。
// 何か付け加えるとしたら、ObjectNotFoundExceptionはどのような追加データを保持すべきですか。

// A. nullは、想定した値が入っていないことをすぐに気付くことができない。
//    一方、その場で例外をスローすれば問題が発生した場所・要因が明確になる。
//    場合によっては原因もその場で判明するため、スローした方がよい。

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

	public final LinkedList<E> find(E obj) throws ObjectNotFoundException{
		LinkedList<E> iter = this;
		while(iter != null) {
			if (iter.curObj.equals(obj)) {
				return iter;
			}
			iter = iter.next();
		}
		throw new ObjectNotFoundException(obj);
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

		Vehicle car2 = new Vehicle();
		car2.setSpeed(100);
		car2.setOwner("Taro");
		System.out.println("---------------------------\n");

		try {
			System.out.println(vs.find(bike1));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println();
		try {
			System.out.println(vs.find(car2));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}