package ch01.ex15;

interface Update extends LookUp {
	void add(String name, Object value);
	Object remove(String name);
}