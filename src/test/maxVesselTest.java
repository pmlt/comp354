import static org.junit.Assert.*;

import org.junit.Test;

/*	Test that only 100 ships can be present in the radar at one time.
 *  If 100 ships are present, new ships should be rejected. */
public class maxVesselTest {

	@Test
	public void test() {
		RadarSimulator rs = new RadarSimulator();
		// Add 100 ships to reach MAX_BOATS
		for (int i = 1; i <= 100; i++)
			rs.addVessel(ve);
		// Add another ship (101 total)
	    assertEquals("Should reject and not add ship", , rs.addVessel(ve));
	}

}
