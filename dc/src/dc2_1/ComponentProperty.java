package dc2_1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.stream.IntStream;

public class ComponentProperty {
	String fontFamily;
	int fontSize;
	Color fgColor;
	Color bgColor;
	public ComponentProperty() {
		this(Font.MONOSPACED, 50, Color.DARK_GRAY, Color.WHITE);
	}
	public ComponentProperty(String fontFamily, int fontSize, Color fgColor, Color bgColor) {
		this.fontFamily = fontFamily;
		this.fontSize = fontSize;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}
	/**
	 * compに設定されているプロパティを取得する
	 * @param comp
	 * @return
	 */
	public static ComponentProperty getProperty(Component comp) {
		String fontFamily = comp.getGraphics().getFont().getFamily();
		int fontSize = comp.getGraphics().getFont().getSize();
		Color fgColor = comp.getForeground();
		Color bgColor = comp.getBackground();
		return new ComponentProperty(fontFamily, fontSize, fgColor, bgColor);
	}
	/**
	 * compにプロパティpropを設定する
	 * @param comp
	 * @param prop
	 */
	public static void setProperty(Component comp, ComponentProperty prop) {
		comp.setFont(new Font(prop.fontFamily, Font.BOLD, prop.fontSize));
		comp.setBackground(prop.fgColor);
		comp.setBackground(prop.bgColor);
	}
	/**
	 * フォント一覧を取得する
	 * @return
	 */
	public static String[] getFontFamilyList() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getAvailableFontFamilyNames();
	}
	/**
	 * フォントサイズの一覧を取得する
	 * @param min
	 * @param max
	 * @return
	 */
	public static int[] getFontSizeList(int min, int max) {
		return IntStream.rangeClosed(min, max).toArray();
	}
}
