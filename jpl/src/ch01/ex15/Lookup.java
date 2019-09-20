package ch01.ex15;

interface LookUp {
	/** return value of relating name
	 * if no value, return null
	 */
	Object find(String name);
}