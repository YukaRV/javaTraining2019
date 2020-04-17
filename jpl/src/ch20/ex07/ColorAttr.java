package ch20.ex07;

import java.io.IOException;

class ColorAttr extends Attr<Object>{
	public static void main(String[] args) {
		ColorAttr ca1 = new ColorAttr("black",0);
		ColorAttr ca2 = new ColorAttr("red","ff0000");
		ColorAttr ca3 = new ColorAttr("black");
		System.out.println(ca1.getColor());
		System.out.println(ca2.getColor());
		System.out.println(ca3.getColor());
		try {
			String fileName = "imageFiles/ch20ex07.txt";
			ca1.write(fileName);
			Attr<Object> res = ca1.read(fileName);
			System.out.println(res);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	private ScreenColor myColor;// 変換された色

	public ColorAttr(String name, Object value) {
		super(name, value);
		decodeColor();
	}

	public ColorAttr(String name) {
		this(name, "transparent");
	}

	public ColorAttr(String name, ScreenColor value) {
		super(name, value.toString());
		myColor = value;
	}

	public Object setValue(Object newValue) {
		// スーパークラスのsetValueを最初に行う
		Object retval = super.setValue(newValue);
		decodeColor();
		return retval;
	}

	/** 値を記述ではなくScreenColorに設定する */
	public ScreenColor setValue(ScreenColor newValue) {
		// スーパークラスのsetValueを最初に行う
		super.setValue(newValue.toString());
		ScreenColor oldValue = myColor;
		myColor = newValue;
		return oldValue;
	}

	/** 変換されたScreenColorオブジェクトを返す */
	public ScreenColor getColor() {
		return myColor;
	}

	/** getValue()で得られる記述からScreenColorを設定する */
	protected void decodeColor() {
		if (getValue() == null)
			myColor = null;
		else
			myColor = new ScreenColor(getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ColorAttr)) return false;
		ColorAttr colorAttr = (ColorAttr)obj;
		// myColorはvalueに対応している
		if (getName().equals(colorAttr.getName()) && getValue().equals(colorAttr.getValue())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// getValue入れられる？
		return (getName()+"_"+getValue()).hashCode();
	}

}