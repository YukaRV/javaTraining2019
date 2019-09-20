package ch01.ex04;

class ASize {
	// 1.4 create a program that generates some sequence (ex: square table)
	public static void main(String[] args) {
		double ratio = Math.sqrt(2);
		int b = 1189;
		int a = (int)(Math.floor(b/ratio));
		int i = 0;
		System.out.println("A size");
		while (i < 8) {
			System.out.println("A"+ (++i) + ": "+a+"x"+b);
			b = a;	// new width (old height)
			a = (int)(Math.floor(b/ratio));	// new height
		}
	}
}
