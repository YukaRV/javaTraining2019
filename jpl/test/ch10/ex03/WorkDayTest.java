package ch10.ex03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class WorkDayTest {

	@Test
	public void escapeIfTest() {
		WorkDay wd = new WorkDay();
		boolean[] expectedList = {false,true,true,true,true,true,false};
		DayOfTheWeek[] DoWs = DayOfTheWeek.values();
		for (int i = 0;i < DoWs.length;i++) {
			boolean actual = wd.isWorkDayIf(DoWs[i]);
			assertEquals(expectedList[i], actual);
		}
   }

	@Test
	public void escapeSwitchTest() {
		WorkDay wd = new WorkDay();
		boolean[] expectedList = {false,true,true,true,true,true,false};
		DayOfTheWeek[] DoWs = DayOfTheWeek.values();
		for (int i = 0;i < DoWs.length;i++) {
			boolean actual = wd.isWorkDaySwitch(DoWs[i]);
			assertEquals(expectedList[i], actual);
		}
   }
}
