package jpl.src.ch01.ex14;

class WalkmanTwoTerm extends Walkman{
	public WalkmanTwoTerm() {
		setTerminal(2);
	}

	@Override
	public int getTerminal() {
		return super.getTerminal();
	}
	@Override
	public void listen() {
		System.out.println("test: listen() (WalkmanTwoTerm)");
	}
	public void listen(int num){// over 2 people
		System.out.println("test: listen(i) (WalkmanTwoTerm)");
	}
}