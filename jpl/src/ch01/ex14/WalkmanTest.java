package ch01.ex14;

class WalkmanTest{
	// 1.14 write walkman structure
	public static void main(String[] args){
		Walkman w1 = new Walkman();
		test(w1);
		Walkman w2 = new WalkmanTwoTerm();
		test(w2);
		Walkman w3 = new WalkmanTwoTermTWC();
		test(w3);
		WalkmanTwoTermTWC w3cast = (WalkmanTwoTermTWC)w3;
		w3cast.twoWayCommunicaton();
	}

	public static void test(Walkman w) {
		w.listen();
		System.out.println(w.getTerminal());
	}
}