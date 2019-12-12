package ch12.ex01;
import org.junit.Test;


public class LinkedListTest {
	@Test(expected = ObjectNotFoundException.class)
	public void refTest() throws ObjectNotFoundException{
		LinkedList<String> list = new LinkedList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		System.out.println(list.find("d"));
	}
}

