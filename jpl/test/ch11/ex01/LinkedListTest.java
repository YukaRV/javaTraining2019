package ch11.ex01;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class LinkedListTest {
	Vehicle car1;
	Vehicle bike1;
	Vehicle bicycle1;
	LinkedList<Vehicle> vs;

	// mainのテスト
	String brRN = "\r\n";
	String brN = "\n";

	@Test
	public void refTest() {
		init();
		LinkedList<Vehicle> vs2 = vs.clone();
		LinkedList<Vehicle> it = vs;
		while (it != null) {
			System.out.println(it.toString());
			it = it.next();
		}
		System.out.println();
		it = vs2;
		while (it != null) {
			System.out.println(it.toString());
			it = it.next();
		}
		// 参照が同じなのでvs2.get()も変わる
		Vehicle car2 = (Vehicle)(vs.get());
		car2.setOwner("Alice");
		LinkedList<Vehicle> iter = vs;
		String str1 = "";
		while (iter != null) {
			str1 += iter.toString() + "\n";
			iter = iter.next();
		}
		iter = vs2;

		String str2 = "";
		while (iter != null) {
			str2 += iter.toString() + "\n";
			iter = iter.next();
		}
		assertEquals(str1, str2);

	}

	@Test
	public void rewriteTest() {
		init();
		LinkedList<Vehicle> vs2 = vs.clone();
		// 参照先を変えてもvs2.get()は変わらない
		vs.set(bicycle1);
		LinkedList<Vehicle> iter = vs;
		iter = vs;

		String str1 = "";
		while (iter != null) {
			str1 += iter + "\n";
			iter = iter.next();
		}

		iter = vs2;

		String str2 = "";
		while (iter != null) {
			str2 += iter.toString() + "\n";
			iter = iter.next();
		}
		assertNotEquals(str1, str2);
	}

	public void init() {
		car1 = new Vehicle();
		bike1 = new Vehicle();
		bicycle1 = new Vehicle();
		vs = new LinkedList<Vehicle>();

		car1.setSpeed(100);
		car1.setOwner("Taro");

		car1.setDegree(45);

		bicycle1.setSpeed(50);
		bicycle1.setDegree(-45);
		bicycle1.setOwner("Hanako");

		vs.add(car1);
		vs.add(bike1);
		vs.add(bicycle1);
	}

	public int diff(String expect,String target) {
		for (int i = 0;i < target.length();i++) {
			int e = expect.length();
			int t = target.length();
			if (i == expect.length() || expect.charAt(i) != target.charAt(i)) {
				char d = expect.charAt(i);
				return i;
			}
		}
		if (expect.length() != target.length()) return target.length();
		return -1;
	}
}

