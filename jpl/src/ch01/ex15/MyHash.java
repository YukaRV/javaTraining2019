package jpl.src.ch01.ex15;

public class MyHash {
	private String[] names = new String[0];
	private Object[] values = new Object[0];

	public Object find(String name) {
		for (int i = 0;i < names.length;i++) {
			if (names[i].equals(name))
				return values[i];
		}
		return null;
	}

	public void add(String name, Object value) {
		int length = 0;
		if (names != null) {
			length = names.length;
		}
		String[] newNames = new String[length+1];
		Object[] newValues = new Object[length+1];
		for (int i = 0;i < length;i++) {
			newNames[i] = names[i];
			newValues[i] = values[i];
		}
		newNames[length] = name;
		newValues[length] = value;

		names = newNames;
		values = newValues;
	}

	public Object remove(String name) {
		Object removeObj = find(name);
		if (removeObj == null) {
			return null;
		}
		boolean isFind = false;
		int newIdx = 0;
		String[] newNames = new String[names.length-1];
		Object[] newValues = new Object[names.length-1];
		for (int i = 0;i < names.length;i++) {
			if (names[i].equals(name) && !isFind) {
				isFind = true;
				continue;
			}
			newNames[newIdx] = names[i];
			newValues[newIdx] = values[i];
			newIdx++;
		}
		names = newNames;
		values = newValues;
		return removeObj;
	}

	public String toString() {
		String result = "{ ";
		for (int i = 0;i < names.length;i++) {
			result += "("+names[i]+":"+(String)values[i]+") ";
		}
		result += "}";
		return result;
	}
}