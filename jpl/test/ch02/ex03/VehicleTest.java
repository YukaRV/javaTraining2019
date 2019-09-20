package ch02.ex03;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class VehicleTest {
   @Test
   public void idTest() {
	   Vehicle v = new Vehicle();
	   Vehicle v2 = new Vehicle();
	   assertEquals(v.getId(), v2.getId()-1);
	   Vehicle v3 = new Vehicle();
	   assertEquals(v2.getId(), v3.getId()-1);
	}
}

