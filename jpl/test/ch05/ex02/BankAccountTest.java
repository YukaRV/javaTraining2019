package ch05.ex02;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class BankAccountTest {

	@Test
	public void historyTest() {
		BankAccount ba = new BankAccount();
		BankAccount ba2 = new BankAccount();
		int balance = 0;
		String[] acts = {"deposit","withdraw", "transfer"};
		int[] amountList = {1000,  50, 100, 120,   5,
								100,  30,   0,   1,  10};
		String[] actList = { acts[0],  acts[1], acts[1],  acts[2], acts[0],
								acts[0], acts[1],  acts[2], acts[1], acts[0]};
		int[] actualAmount = {100, 120, 120,   5,
								100,  30,   0,   0,   1,  10};
		// acts[2]=transferの前にはdepositが入る
		String[] actualAct = { acts[1], "deposit", acts[2],acts[0],
								acts[0], acts[1], "deposit",  acts[2], acts[1], acts[0]};
		for (int i = 0;i < actList.length;i++) {
			String act = actList[i];
			int amount = amountList[i];
			switch (act) {
			case "deposit":
				ba.deposit(amount);
				balance += amount;
				break;
			case "withdraw":
				ba.withdraw(amount);
				balance -= amount;
				break;
			case "transfer":
				ba.transfer(ba2, amount);
				balance += amount;
				break;
			default:
				System.out.println("action error");
				break;
			}
			assertEquals(balance, ba.getBalance());
		}

		BankAccount.History h = ba.history();
		BankAccount.Action action;
		int actIdx = 0,amountIdx = 0;
		while((action = h.next()) != null) {
			System.out.println(action);
			String expect = ba.getNumber()+": "+actualAct[actIdx]+" "+actualAmount[amountIdx];
			assertEquals(expect, action.toString());
			actIdx++;amountIdx++;
		}
	}
}
