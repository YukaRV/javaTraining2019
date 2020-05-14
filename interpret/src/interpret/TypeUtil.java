package interpret;

import java.lang.reflect.Array;
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
	public static<T> T of(Object value, Class<T> cls) throws Exception {
		try {
			Object wrpObj;
			if (wrpClass.containsKey(cls)) {
				Class<?> castClass = wrpClass.get(cls);
				Class<?> curClass = value.getClass();
				if (castClass.equals(curClass)) {
					return (T)value;
				}
				System.out.println(castClass.equals(Character.class));
				System.out.println(curClass.equals(String.class));
				if (castClass.equals(Character.class) && curClass.equals(String.class)) {
					char[] valChar = String.valueOf(value).toCharArray();
					value = valChar[0];
					curClass = char.class;
				}
//				Class<?> strClass = String.class;
				Constructor<?> constructor = castClass.getConstructor(curClass);
				wrpObj = constructor.newInstance(value);
				Method m;
				m = wrpObj.getClass().getMethod(cls.toString() + "Value");

				return (T)m.invoke(wrpObj);
			} else {
				Class<?> castClass = cls;
				Constructor<?> constructor = castClass.getConstructor(String.class);
				return (T)constructor.newInstance(value);
			}
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
		    if (cause instanceof Error) {
		      throw (Error) cause;
		    } else if (cause instanceof RuntimeException) {
		      throw ((RuntimeException) cause);
		    } else if (cause instanceof Exception) {
		      throw new RuntimeException(cause);
		    } else {
		      throw new InternalError(cause);
		    }
		}
    }

	public static<T> T[] castArray(Object[] array, Class<T> cls){
		try {
			System.out.println(cls.getName());
			Class<?> castClass;
			if (wrpClass.containsKey(cls)) {
				castClass = wrpClass.get(cls);
			} else {
				castClass = cls;
			}
			T[] res = (T[])Array.newInstance(castClass, array.length);
			for (int i = 0;i < res.length;i++) {
				res[i] = (T)of(array[i],cls);
			}
			return res;
		} catch (Exception e) {
			System.out.println(e);
			return null;
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
