package ch02.ex16;
import static org.junit.Assert.*;

import org.junit.Test;

import ch02.ex16.LinkedList;
import ch02.ex16.Vehicle;

public class LinkedListTest {
   @Test
   public void sizeTest() {
	   int length = 58;
	   LinkedList root = new LinkedList();
	   LinkedList curL = root;
	   Vehicle v;
	   root.set(new Vehicle());
	   for (int i = 1;i < length;i++) {
		   curL.setNext(new Vehicle());
		   curL = curL.next();
	   }
		assertEquals(length,root.size());
	}
}

