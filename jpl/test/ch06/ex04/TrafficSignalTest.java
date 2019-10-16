package ch06.ex04;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.Test;

public class TrafficSignalTest {

   @Test
   public void trafficColorTest() {
	   Color red = new Color(255,0,0);
	   Color yellow = new Color(255,255,0);
	   Color blue = new Color(0,0,255);
	   Color redTraffic = TrafficSignal.RED.getColor();
	   Color yellowTraffic = TrafficSignal.YELLOW.getColor();
	   Color blueTraffic = TrafficSignal.BLUE.getColor();
	   assertEquals(red, redTraffic);
	   assertEquals(yellow, yellowTraffic);
	   assertEquals(blue, blueTraffic);
   }
}

