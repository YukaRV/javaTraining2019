package ch11.ex02;

class ColorAttr extends Attr<Object>{
	public static void main(String[] args) {
		ColorAttr ca1 = new ColorAttr("black",0);
		ColorAttr ca2 = new ColorAttr("red","ff0000");
		ColorAttr ca3 = new ColorAttr("black");
		ColorAttr ca4 = new ColorAttr("black",0);
		System.out.println(ca1.getColor());
		System.out.println(ca2.getColor());
		System.out.println(ca3.getColor());
		System.out.println(ca4.getColor());
		System.out.println(ca1.hashCode());
		System.out.println(ca2.hashCode());
		System.out.println(ca3.hashCode());
		System.out.println(ca4.hashCode());
		System.out.println(ca1.equals(ca2));
		System.out.println(ca2.equals(ca3));
		System.out.println(ca3.equals(ca1));
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