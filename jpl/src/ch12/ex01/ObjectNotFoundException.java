package ch12.ex01;

class ObjectNotFoundException extends Exception {
	Object obj;
	public ObjectNotFoundException(Object obj) {
		this.obj = obj;
	}
}
