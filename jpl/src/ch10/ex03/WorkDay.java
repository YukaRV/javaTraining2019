package ch10.ex03;

// 10.3
// 練習問題6.1の｢週の曜日｣を表すenumを使用して、
// 曜日を受け取ってその日が働く日であればtrueを返し
// そうでなければfalseを返すメソッドを書きなさい。
// 最初にネストしてif-else文を使用して、
// それから、switch文を使用しなさい。どちらが明瞭なコードだと考えますか。

// switch文の方が明瞭である。
// if文ではクラス名まで書かなければならない、等式を書かなければならない等
// より詳しく条件を書く必要がある。しかし、switch文は値の一致で条件分岐するため、
// 今回のようにenumのどれか判定するのであれば不必要に文章を長くしなくて済む。

public class WorkDay {
	boolean isWorkDayIf(DayOfTheWeek DoW) {
		if (DoW == DayOfTheWeek.SUNDAY || DoW == DayOfTheWeek.SATURDAY) {
			return false;
		} else {
			return true;
		}
	}
	boolean isWorkDaySwitch(DayOfTheWeek DoW) {
		switch (DoW) {
		case SUNDAY:
		case SATURDAY:
			return false;
		default:
			return true;
		}
	}
}