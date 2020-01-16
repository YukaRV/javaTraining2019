package ch16.ex09;

import ch16.ex04.ReflectAnnotationExample.ConstructorAnnotaion;
import ch16.ex04.ReflectAnnotationExample.FieldAnnotation;
import ch16.ex04.ReflectAnnotationExample.MethodAnnotation;

public class TestClass<E> implements Runnable{
	@FieldAnnotation
	public double publicField;
	@FieldAnnotation
	public long publicField2;

	@ConstructorAnnotaion
	public TestClass() {
	}

	@MethodAnnotation
	public void publicMethod() {

	}

	@MethodAnnotation
	public String publicMethod2(String x, int y) {
		return "";
	}
	@MethodAnnotation
	protected int protectedMethod() {
		return 0;
	}

	public class Cell implements Cloneable{
		private Cell next;
		private E element;
		public Cell() {
		}
	}

	@Override
	public void run() {
	}

}