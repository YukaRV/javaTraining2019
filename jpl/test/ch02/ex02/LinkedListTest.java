package jpl.test.ch02.ex02;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import jpl.src.ch02.ex02.LinkedList;

public class LinkedListTest {
   @Test
   public void initTest() {
	   LinkedList l = new LinkedList();
	   Object[] obj = {1,"str",'c',1.0f};
	   for (int i = 0;i < obj.length;i++) {
		   l.set(obj[i]);
		   l.setNext(obj[(i+1)%obj.length]);
		   assertEquals(obj[i], l.get());
		   assertEquals(obj[(i+1)%obj.length], l.next().get());
	   }
	}
}

