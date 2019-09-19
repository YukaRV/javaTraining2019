package jpl.src.ch01.ex15;

class MyHashTest {
	// 1.15 write interface extends Lookup
	public static void main(String[] args) {
		MyHash h = new MyHash();
		Object a = "value";
		h.add("test1","value1");
		h.add("test2","value2");
		h.add("test3","value3");
		h.add("test4","value4");
		h.remove("test3");
		System.out.println(h);

		for (int i = 0;i < 4;i++) {
			Object o = h.find("test"+(i+1));
			System.out.println(o);
		}
	}
}