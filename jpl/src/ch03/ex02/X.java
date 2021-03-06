package ch03.ex02;

public class X {
	{
		printVariable("初期値"); // 191115 追記: ここは手順3
	}
	protected int xMask = 0x00ff;
	protected int fullMask;

	public X() {
		printVariable("4. Xのフィールドを初期化");
		fullMask = xMask;
		printVariable("5. Xのコンストラクタが実行される");
	}

	public int mask(int orig) {
		return (orig & fullMask);
	}

	public void printVariable(String title) {
		System.out.println(title);
		System.out.println("xMask yMask fullMask");
		System.out.printf("0x%04x 0x%04x 0x%04x\n",xMask,0,fullMask);
	}

}
