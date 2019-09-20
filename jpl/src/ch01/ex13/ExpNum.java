package ch01.ex13;

class ExpNum{
	int num;
	boolean odd;
	ExpNum(int num) {
		setVal(num);
	}
	void setVal(int num) {
		this.num = num;
		setOdd();
	}
	void setOdd() {
		if (num % 2 == 0)
			odd = true;
		else
			odd = false;
	}
}
