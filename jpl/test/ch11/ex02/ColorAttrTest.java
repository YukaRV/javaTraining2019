package ch11.ex02;
import static org.junit.Assert.*;

import org.junit.Test;

//Vehicleを変更して、コンストラクタでVehicleと関連付けされるEnergySourceオブジェクトの
//参照を持つようにしなさい。EnergySourceクラスは、abstractクラスでなければなりません。
//なぜならば、GasTankオブジェクトの満タンの測定の方法は、Batteryオブジェクトとは異なるでしょう。
//EnergySourceにabstractのemptyメソッドを入れて、GasTankとBatteryクラスでそのメソッドを
//実装しなさい。動力源が空でないことを保証するstartメソッドをVehicleに追加しなさい。

public class ColorAttrTest {

	String brRN = "\r\n";
	// mainのテスト
	@Test
	public void equalsTest() {
		ColorAttr ca1 = new ColorAttr("black",0);
		ColorAttr ca2 = new ColorAttr("red",0);
		ColorAttr ca3 = new ColorAttr("black");
		ColorAttr ca4 = new ColorAttr("black",0);
		assertNotEquals(ca1.hashCode(),ca2.hashCode());
		assertNotEquals(ca1.hashCode(),ca3.hashCode());
		assertEquals(ca1.hashCode(),ca4.hashCode());
		assertNotEquals(ca2.hashCode(),ca3.hashCode());
		assertNotEquals(ca2.hashCode(),ca4.hashCode());
		assertNotEquals(ca3.hashCode(),ca4.hashCode());
	}

	@Test
	public void hashCodeTest() {
		ColorAttr ca1 = new ColorAttr("black",0);
		ColorAttr ca2 = new ColorAttr("red",0);
		ColorAttr ca3 = new ColorAttr("black");
		ColorAttr ca4 = new ColorAttr("black",0);
		assertEquals(false,ca1.equals(ca2));
		assertEquals(false,ca1.equals(ca3));
		assertEquals(true,ca1.equals(ca4));
		assertEquals(false,ca2.equals(ca3));
		assertEquals(false,ca2.equals(ca4));
		assertEquals(false,ca3.equals(ca4));
	}
}

