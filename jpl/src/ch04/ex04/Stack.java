package ch04.ex04;
// 4.4
// インタフェースのみ使用して、コレクションクラス階層を設計しなさい。

// コレクションクラス: List, Queueなど。Iteratorで内容にアクセスしたりする。


public interface Stack<E> extends Collection<E>{
	public void push(E obj);
	public E pop();

}
