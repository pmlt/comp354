package tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.google.gson.JsonSyntaxException;


import common.Coord;
import common.Course;
import common.UpdateData;
import common.Vessel.VesselType;

public class UpdateDataTest {

	@Test
	public void testJSON() {
		Calendar timestamp = Calendar.getInstance();
		UpdateData test = new UpdateData("myid", VesselType.SPEED_BOAT, new Coord(10, 10), new Course(20, 20), timestamp);
		
		String testJSON = test.toJSON();
		UpdateData result = UpdateData.fromJSON(testJSON);
		
		assertEquals(test.Id, result.Id);
		assertEquals(test.Type, result.Type);
		assertEquals(test.Timestamp, result.Timestamp);
		assertEquals(test.Coordinates, result.Coordinates);
		assertEquals(test.Course, result.Course);
		
		String badjson = "{\"id\":[1,2,3],\"type\":\"WRONGENUMVALUE\",\"coords\":0,\"course\":{}}";
		boolean success = false;
		try {
			result = UpdateData.fromJSON(badjson);
		}
		catch (JsonSyntaxException e) {
			success = true;
		}
		finally {
			assertTrue(success);
		}
	}

}
