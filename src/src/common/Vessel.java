package common;

import java.util.*;

import vms.Coord;
import vms.Course;

public class Vessel {
	public enum VesselType {
		BOAT
		// XXX Add all supported types here
	}
	
	public Vessel(String id, VesselType type) {
		// XXX Initialize members
	}
	
	public String getId() {
		return ""; // XXX
	}
	
	public VesselType getType() {
		return VesselType.BOAT; // XXX
	}
	
	public Coord getCoord(Calendar timestamp) {
		// XXX find timestamp of LATEST snapshot that comes before provided timestamp
		// Calculate difference between provided timestamp and snapshot timestamp
		// If = 0, output coordinates of the snapshot
		// If > 0, calculate current coordinates based on snapshot's course
		return new Coord(0,0);
	}
	
	public Course getCourse(Calendar timestamp) {
		// XXX find timestamp of LATEST snapshot that comes before provided timestamp
		// Return course of found snapshot
		return new Course(0,0);
	}
	
	public Calendar getLastTimestamp() {
		// XXX Return timestamp of latest snapshot
		return Calendar.getInstance();
	}
	
	public void update(Coord newCoords, Course newCourse, Calendar timestamp) {
		// XXX Record new snapshot of location/course at the provided timestamp
	}
	
	public void update(UpdateData data) {
		// XXX Record new snapshot of location/course based on UpdateData
		// should throw if ID/Type does not match
	}
	
	public UpdateData getUpdateData(Calendar timestamp) {
		// XXX Generate an UpdateData instance based on the info of the
		// vessel at the specified timestamp
		return new UpdateData();
	}
}
