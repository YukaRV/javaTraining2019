package ch02.ex02;

public class LinkedList{
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
}