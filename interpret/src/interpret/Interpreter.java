package interpret;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.swing.JOptionPane;

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
	public Object getNewObject(String objectClass) {
		Class<?> cls;
		Object obj;
		try {
			cls = Class.forName(objectClass);
		} catch (ClassNotFoundException e) {
			exception.printStackTrace(e);
			return null;
		}

		try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		return obj;
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
		return variable.getClass().getFields();
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
			return variable.getClass().getField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			printError(e);
			return null;
		}
	}

	public boolean updateField(String variableName, String fieldName, String updateValue) {
		return updateField(getVariable(variableName), getField(variableName, fieldName), updateValue);
	}
	public boolean updateField(Object variable, Field field, String updateValue) {
		Class<?> type = field.getType();
		Object updateObj = typeCast(updateValue, type);
		if (updateObj == null) {
			return false;
		}
		field.setAccessible(true);
		try {
			print(field.get(variable));

			field.set(variable, updateObj);
			print(field.get(variable));

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
		return variable.getClass().getMethods();
	}
	public Method getMethod(String variableName, String methodName) {
		return getMethod(getVariable(variableName), methodName);
	}
	public Method getMethod(Object variable, String methodName) {
		try {
			return variable.getClass().getMethod(methodName);
		} catch (NoSuchMethodException | SecurityException e) {
			printError(e);
			return null;
		}
	}
	/**
	 * TODO (Class<Type>,Object)[]を引数にとれるよう変更
	 * @param variableName
	 * @param methodName
	 * @param args
	 */
	public Object executeMethod(String variableName, String methodName, String[] args) {
		return executeMethod(variableName, methodName, args);
	}
	public Object executeMethod(Object variable, String methodName, String[] args) {
		return executeMethod(variable, getMethod(variable, methodName), args);
	}
	public Object executeMethod(Object variable, Method method, String[] args) {
		Type[] types = method.getGenericParameterTypes();
		Object[] argsObj = new Object[args.length];
		for (int i = 0;i < args.length;i++) {
			Class<?> cls;
			try {
				String typeName = types[i].getTypeName();
				cls = TypeUtil.wrpStrClass.containsKey(typeName) ? TypeUtil.wrpStrClass.get(typeName):Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				exception.printStackTrace(e);
				return null;
			}
			Object castedArg = typeCast(args[i],cls);
			if (castedArg == null) {
				JOptionPane.showMessageDialog(null, "失敗");
				return null;
			}
			argsObj[i] = castedArg;
		}
		// TODO 引数の型変換
		return executeMethod(variable, method, argsObj);
	}
	private Object executeMethod(Object variable,Method method, Object ...args) {
		try {
			method.setAccessible(true);
			return method.invoke(variable, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			printError(e);
			return null;
		}
	}

}
