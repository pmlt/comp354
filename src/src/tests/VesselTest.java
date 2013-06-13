package tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import vms.*;

import org.junit.Test;

public class VesselTest {

	@Test
	public void test() {
		String id = "myid";
		VesselType type = VesselType.Boat;
		Coord startCoord = new Coord(0,0);
		Course course = new Course(5,5);
		Calendar startTime = Calendar.getInstance();
		
		Vessel v = new Vessel(id, type);
		v.update(startCoord, course, startTime);
		
		assertEquals(id, v.getId());
		assertEquals(type, v.getType());
		assertEquals(startCoord, v.getCoord(startTime));
		assertEquals(course, v.getCourse());
		assertEquals(startTime, v.getStartTime());
		
		//Assume two seconds have passed; should have traveled exactly 10 meters
		Calendar curTime = (Calendar)startTime.clone();
		curTime.add(Calendar.SECOND, 2);
		assertEquals(new Coord(10, 10), v.getCoord(curTime));
		
		//Travel another 5 meters, then change course
		curTime.add(Calendar.SECOND, 1);
		Course newCourse = new Course(0, -10);
		v.update(v.getCoord(curTime), newCourse, curTime);
		assertEquals(newCourse, v.getCourse());
		assertEquals(new Coord(15, 15), v.getCoord(curTime));
		
		//Travel for two seconds, see if the new course was taken into account
		curTime.add(Calendar.SECOND, 2);
		assertEquals(new Coord(15, -5), v.getCoord(curTime));
	}

}
