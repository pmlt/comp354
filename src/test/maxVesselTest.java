import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayDeque;

/*	Test that only 100 ships can be present in the radar at one time.
 *  If 100 ships are present, new ships should be rejected. */
public class maxVesselTest {

	@Test
	public void test() {
		RadarSimulator rs = new RadarSimulator();
		// Add 100 ships to reach MAX_BOATS
		for (int i = 1; i <= 100; i++) {
			Vessel ve = new Vessel(i.toString(),1,0,0,0,0,0);
			rs.addVessel(ve);
		}
		// Add another ship (101 total)
	    assertEquals("Should be added to queue, Not table or radar", queue[0] = veExtra, rs.addVessel(veExtra));
	}

}
