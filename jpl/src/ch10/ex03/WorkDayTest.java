package ch10.ex03;

public class WorkDayTest {
	public static void main(String[] args) {
		WorkDay wd = new WorkDay();
		for (DayOfTheWeek DoW:DayOfTheWeek.values()) {
			System.out.println(wd.isWorkDayIf(DoW));
			System.out.println(wd.isWorkDaySwitch(DoW));
		}
	}
}