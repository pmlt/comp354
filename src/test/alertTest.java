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
		RadarSimulator rs = new RadarSimulator();
		rs.addVessel(ve)
		VMS tester = new VMS();
		tester.update(rs)
	}
	
	@Test
	public void testAlerts(rs) {
		TablePanel table = new TablePanel();
		assertEquals("High Risk = Red", 50, table.update(rs));
	}

}
