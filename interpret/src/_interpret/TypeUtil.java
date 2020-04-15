package _interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

// bool,char,byte,short,int,long,float,double
public class TypeUtil {
	public static HashMap<Class<?>, Class<?>> wrpClass = new HashMap<>();
	public static HashMap<String, Class<?>> wrpStrClass = new HashMap<>();
	static {
		wrpClass.put(boolean.class, Boolean.class);
		wrpClass.put(char.class, Character.class);
		wrpClass.put(byte.class, Byte.class);
		wrpClass.put(short.class, Short.class);
		wrpClass.put(int.class, Integer.class);
		wrpClass.put(long.class, Long.class);
		wrpClass.put(float.class, Float.class);
		wrpClass.put(double.class, Double.class);
		wrpStrClass.put("boolean", boolean.class);
		wrpStrClass.put("char", char.class);
		wrpStrClass.put("byte", byte.class);
		wrpStrClass.put("short", short.class);
		wrpStrClass.put("int", int.class);
		wrpStrClass.put("long", long.class);
		wrpStrClass.put("float", float.class);
		wrpStrClass.put("double", double.class);
	}
	// 型がプログラマ視点で分かる場合のキャスト
	public static <T> T of(Object value) {
        return (T) value;
    }
	// 型がプログラマ視点で分からない場合のキャスト
	public static Object of(Object value, Class<?> cls) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object wrpObj;
		if (wrpClass.containsKey(cls)) {
			Class<?> castClass = wrpClass.get(cls);
			Constructor<?> constructor = castClass.getConstructor(String.class);
			wrpObj = constructor.newInstance(value);
			Method m;
			m = wrpObj.getClass().getMethod(cls.toString() + "Value");

			return m.invoke(wrpObj);
		} else {
			Class<?> castClass = cls;
			Constructor<?> constructor = castClass.getConstructor(String.class);
			return constructor.newInstance(value);
		}
    }

	public static boolean isInteger(String num) {
		try {
	        Integer.parseInt(num);
	        return true;
	        } catch (NumberFormatException e) {
	        return false;
	    }
	}
}
