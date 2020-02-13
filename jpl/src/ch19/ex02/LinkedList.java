package ch19.ex02;

/**
 * <code>LinkedList</code>は、片方向リストを表す。
 * 各々の<code>LinkedList</code>オブジェクトはノードを表し、
 * 格納する<code>Object</code>型の値と、次のノードである<code>LinkedList</code>をもつ。
 * @version 1.0
 * @author yk
 * @since 1.0
 *
 */
public class LinkedList{
	/** 格納する値 */
	private Object obj;
	/** 次のノード */
	private LinkedList nextObj;
	/**
	 * 値を格納する
	 * @param obj 格納するObject
	 */
	public void set(Object obj) {
		this.obj = obj;
	}
	/**
	 * 次のノードを設定する
	 * @param list 次のノード
	 */
	public void setNext(LinkedList list) {
		nextObj = list;
	}
	/**
	 * 次のノードを、objを持った<code>LinkeList</code>として新たに生成し、設定する
	 * @param obj 次のノードが格納する値
	 */
	public void setNext(Object obj) {
		nextObj = new LinkedList();
		nextObj.set(obj);
	}
	/**
	 * 格納している値を取得する
	 * @return 格納している値
	 */
	public Object get() {
		return obj;
	}
	/**
	 * 次のノードを取得する
	 * @return 次のノード
	 */
	public LinkedList next() {
		return nextObj;
	}
	/**
	 * 自分自身を1つ目として、要素がいくつ続いているか(長さ)を取得する
	 * @return 要素の長さ
	 */
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

		LinkedList iter = vs;

		while (iter != null) {
			System.out.println(iter);
			System.out.println();
			iter = iter.next();
		}
		System.out.println(vs.size());
	}
}