package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Interpreter {
	private HashMap<String, Object> variableList;
	ConsolePanel console;
	ExceptionPanel exception;
	public Interpreter() {
		this(null, null);
	}
	public Interpreter(ConsolePanel console, ExceptionPanel exception) {
		this.console = console;
		this.exception = exception;
		variableList = new HashMap<>();
	}

	public HashMap<String, Object> getVariables() {
		return variableList;
	}
	/**
	 * 引数なしインスタンスを生成<br/>
	 * やってることはclassをStringから生成してexecuteConstructorだけだから統合して消していい
	 * @param objectClass
	 * @return
	 */
	public Class<?> getClass(String objectClass) {
		Class<?> cls;
		try {
			cls = Class.forName(objectClass);
		} catch (ClassNotFoundException e) {
			exception.printStackTrace(e);
			return null;
		}
//		obj = executeConstructor(cls);
		return cls;
	}
	/**
	 * 変数の追加
	 * @param variableName
	 * @param object
	 */
	public Object setVariable(String variableName, Object object) {
		return variableList.put(variableName, object);
	}
	public boolean hasVariable(String variableName) {
		return variableList.containsKey(variableName);
	}
	/**
	 * 変数を返す
	 * @param variableName
	 * @return
	 */
	public Object getVariable(String variableName) {
		if (hasVariable(variableName))
			return variableList.get(variableName);
		return null;
	}

	public boolean hasField(String variableName, String fieldName) {
		return hasField(getVariable(variableName), fieldName);
	}
	public boolean hasField(Object variable, String fieldName) {
		return getField(variable, fieldName) != null;
	}
	public Field[] getFields(String variableName) {
		return getFields(getVariable(variableName));
	}
	public Field[] getFields(Object variable) {
		Class<?> cls = variable.getClass();
		Field[] curAllField = cls.getDeclaredFields();
		Field[] publicAllField = cls.getFields();
		ArrayList<Field> allField = new ArrayList<>();
		for (Field curField: curAllField) {
			if (!isPublic(curField)) allField.add(curField);
		}
		allField.addAll(Arrays.asList(publicAllField));
		Field[] result = new Field[allField.size()];
		return allField.toArray(result);
	}
	public Field getField(String variableName, String fieldName) {
		return getField(getVariable(variableName),fieldName);
	}
	/**
	 * objectの特定のフィールドを返す
	 * @param variable
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public Field getField(Object variable, String fieldName) {
		try {
			Field[] allField = getFields(variable);
			for (Field field: allField) {
				if (field.getName().equals(fieldName)) {
					return field;
				}
			}
			return null;
		} catch (SecurityException e) {
			printError(e);
			return null;
		}
	}
	public Object getFieldObject(String variableName, String fieldName) {
		Field field = getField(variableName, fieldName);
		Object object;
		try {
			field.setAccessible(true);
			object = field.get(getVariable(variableName));
			return object;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			printError(e);
			return null;
		}
	}

	public boolean updateField(String variableName, String fieldName, Object updateValue) {
		return updateField(getVariable(variableName), getField(variableName, fieldName), updateValue);
	}
	public boolean updateField(Object variable, Field field, Object updateValue) {
		Class<?> type = field.getType();
		Object updateObj = typeCast(updateValue, type);
		if (updateObj == null) {
			return false;
		}
		field.setAccessible(true);
		try {
			String resultStr = "update: "
								+ stringExpression(field.get(variable))
								+ " -> ";

			field.set(variable, updateObj);

			resultStr += stringExpression(field.get(variable));
			print(resultStr);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			printError(e);
			return false;
		}
		return true;
	}

	public boolean hasMethod(String variableName, String methodName) {
		return hasMethod(getVariable(variableName), methodName);
	}
	public boolean hasMethod(Object variable, String methodName) {
		return true;
	}
	public Method[] getMethods(String variableName) {
		return getMethods(getVariable(variableName));
	}
	public Method[] getMethods(Object variable) {
		Method[] mtds1 = variable.getClass().getMethods();
		Method[] mtds2 = variable.getClass().getDeclaredMethods();
		ArrayList<Method> mtds = new ArrayList<>();
		for (Method m: mtds1) {
			if (m.getDeclaringClass() == Object.class)
				continue;
			if (isPublic(m))
				continue;
			mtds.add(m);
		}
		mtds.addAll(Arrays.asList(mtds2));
		return variable.getClass().getMethods();
	}
	public Method getMethod(String variableName, String methodName, Class<?> ...parametricTypes) {
		return getMethod(getVariable(variableName), methodName, parametricTypes);
	}
	public Method getMethod(Object variable, String methodName, Class<?> ...parametricTypes) {
		Class<?> cls = variable.getClass();
		try {
			Method mtd = cls.getMethod(methodName, parametricTypes);
			return mtd;
		} catch (NoSuchMethodException | SecurityException e) {
		}
		try {
			return cls.getDeclaredMethod(methodName, parametricTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			printError(e);
			return null;
		}
	}

	/**
	 * メソッドを実行する<br/>
	 * 引数の型が分かっていないと実行できないため、Methodの特定が必須
	 * @param variable
	 * @param method
	 * @param args
	 */
	public Object executeMethod(Object variable, Method method, Object ...args) {
		try {
			method.setAccessible(true);
			return method.invoke(variable, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			printError(e);
			return null;
		}
	}

	public Constructor<?>[] getConstructors(String variableName) {
		return getConstructors(getVariable(variableName));
	}
	public Constructor<?>[] getConstructors(Object variable) {
		return getConstructors(variable.getClass());
	}
	public Constructor<?>[] getConstructors(Class<?> variableClass) {
		return variableClass.getConstructors();
	}
	public Constructor<?> getConstructor(String variableName, Class<?> ...parametricTypes) {
		return getConstructor(getVariable(variableName), parametricTypes);
	}
	public Constructor<?> getConstructor(Object variable, Class<?> ...parametricTypes) {
		try {
			return variable.getClass().getConstructor(parametricTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			printError(e);
			return null;
		}
	}
	public Object executeConstructor(Class<?> cls, Object ...args) {
		try {
			return executeConstructor(cls.getConstructor(),args);
		} catch (NoSuchMethodException | SecurityException e) {
			printError(e);
			return null;
		}
	}
	public Object executeConstructor(Constructor<?> constructor, Object ...args) {
		try {
			constructor.setAccessible(true);
			if (args.length == 0)
				return constructor.newInstance();
			// String[]とintの引数ならうまくいく。
			// char[]とかプリミティブ配列がだめ
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			printError(e);
			return null;
		}
	}

	private <T> void print(T obj) {
		if (console == null) {
			System.out.println(obj);
		} else {
			console.print(obj);
		}
	}
	private void printError(Exception e) {
		if (exception == null) {
			e.printStackTrace();
		} else {
			exception.printStackTrace(e);
		}
	}
	private Object typeCast(Object obj,Class<?> cls) {
		try {
			return TypeUtil.of(obj,cls);
		} catch (Exception e) {
			printError(e);
			return null;
		}
	}

	static boolean isPublic(Member mem) {
		return ((mem.getModifiers() & Modifier.PUBLIC) != 0);
	}
	static String stringExpression(Object obj) {
		Class<?> objType = obj.getClass();
		if (objType.equals(String.class)) {
			return "\""+obj.toString()+"\"";
		}
		else {
			return obj.toString();
		}
	}

	private void throwError(InvocationTargetException e) {
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
