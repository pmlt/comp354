import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

/*	Test that different alert levels change the color of the table row(s)
 *  and the icon(s) on the radar to:
 *	RED	for HIGH level risk,
 *	YELLOW for LOW level risk,
 *  and WHITE for NONE (no risk). */
public class alertTest {

	@BeforeClass
	public static void testSetup() {
		// Vessel 1 and 2 should generate Low level risk (yellow)
		Vessel vessel1 = new Vessel("01",1,0,0,0,0,0);
		Vessel vessel2 = new Vessel("02",1,100,0,0,0,0);
		// Vessel 3 and 4 should generate High level risk (red)
		Vessel vessel3 = new Vessel("03",1,500,500,0,0,0);
		Vessel vessel4 = new Vessel("04",1,525,500,0,0,0);
		RadarSimulator rs = new RadarSimulator();
		rs.addVessel(vessel1);
		rs.addVessel(vessel2);
		rs.addVessel(vessel3);
		rs.addVessel(vessel4);
		VMS tester = new VMS(rs);
		tester.update();
		boolean[] a1 = new boolean[5];
		for (int i = 0; 0 <= 4; i++)
			a1[i] = true;
	}
		
	@Test
	public void testAlerts() {
		// Table color not implemented yet
		// Test map color coding:
	}

}
