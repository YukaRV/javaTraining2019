package ch04.ex04;
// 4.4
// インタフェースのみ使用して、コレクションクラス階層を設計しなさい。

// コレクションクラス: HashMapなど。Iteratorでないようにアクセスしたりする。


public interface List<E> extends Collection<E>{
	public void add(E obj);
	public E remove();

}
