package ch03.ex07;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenColor {
	private int r=0,g=0,b=0,a=0;

	public ScreenColor(Object value) {
		// 判定に困らない程度のそれっぽい計算
		if (value.equals("transparent")) {
			r = g = b = 0;
			a = 255;
		}
		if (value instanceof String) {
			String strValue = (String)value;
			Matcher m = Pattern.compile("^[0-9a-fA-F]{6}$").matcher(strValue);
			if (m.find()) {
				r = Integer.parseInt(strValue.substring(0,2),16);
				g = Integer.parseInt(strValue.substring(2,4),16);
				b = Integer.parseInt(strValue.substring(4,6),16);
				a = 0;
			}
		}
	}

	@Override
	public String toString() {
		return "("+r+","+g+","+b+","+a+")";
	}
}
