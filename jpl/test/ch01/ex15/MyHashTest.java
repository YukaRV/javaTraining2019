package jpl.test.ch01.ex15;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import jpl.src.ch01.ex15.MyHash;

public class MyHashTest {
	MyHash h = new MyHash();

	@Test
	public void insertAndDeleteTest() {
		String[] names = {"test1","test2","test3","test4"};
		Object[] values = {"value",'s',0,1.0f};
		int length = names.length;
		System.out.println(h);
		for (int i = 0;i < length;i++) {
			h.add(names[i], values[i]);
		}

		for (int i = 0;i < length;i++) {
			assertEquals(values[i], h.find(names[i]));
		}

		int removeIdx = 2;
		assertEquals(values[removeIdx],h.remove(names[removeIdx]));
		assertNull(h.find(names[removeIdx]));
	}

//	@Test
//	public void deleteTest() {
//		int removeIdx = 2;
//		assertEquals(values[removeIdx],h.remove(names[removeIdx]));
//		assertNull(h.find(names[removeIdx]));
//	}
}

