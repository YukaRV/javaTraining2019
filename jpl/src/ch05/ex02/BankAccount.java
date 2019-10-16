package ch05.ex02;

import java.util.LinkedList;


// 5.2
// Q. 口座に対する最後の10個の処理を記録するBankAccountを作成しなさい。
//    historyメソッドを追加して、Historyオブジェクトを返すようにしなさい。
//    Historyオブジェクトは、nextメソッドでActionオブジェクトを1つ返して、
//    リストの最後ではnullを返すようにしなさい。
//    Historyはネストしたクラスにすべきですか。ネストしたクラスにすべきなら、
//    staticにすべきですか。
// A. ネストされたクラスであるActionにかかわるものなので、BankAccount内にネストすべきである。
//    しかし、契約には関係ないためstaticにすべきではない。

public class BankAccount {
	private long number; // 口座番号
	private long balance; // 現在の残高(単位はセント)
	private int historyLength = 10;
	private History lastActHistory = new History();

	public long getNumber() {
		return number;
	}
	public long getBalance() {
		return balance;
	}

	public void deposit(long amount) {
		balance += amount;
		lastActHistory.add(new Action("deposit", amount));
	}

	public void withdraw(long amount) {
		balance -= amount;
		lastActHistory.add(new Action("withdraw", amount));
	}

	public void transfer(BankAccount other, long amount) {
		other.withdraw(amount);
		deposit(amount);
		lastActHistory.add(this.new Action("transfer", amount));
		other.lastActHistory.add(other.new Action("transfer", amount));
	}

	public History history() {
		return lastActHistory;
	}

	public class Action {
		private String act;
		private long amount;
		Action(String act, long amount) {
			this.act = act;
			this.amount = amount;
		}
		public String toString() {
			// identify our enclosing account
			return number + ": " + act + " " + amount;
		}
	}

	public class History {
		private LinkedList<Action> histories;
		private int curIdx;
		public History() {
			histories = new LinkedList<>();
			curIdx = 0;
		}
		public void add(Action lastAct) {
			histories.add(lastAct);
			int length = histories.size();
			if (length == historyLength+1) {
				histories.removeFirst();
			}
		}
		public void resetIndex() {
			curIdx = 0;
		}
		public Action next() {
			if (curIdx > histories.size()-1) return null;
			return histories.get(curIdx++);
		}
	}

}
