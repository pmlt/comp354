package common;

import java.util.*;

import vms.Coord;
import vms.Course;

public class Vessel {
	private VesselType type;
	private String id;
	private Course course;
	private Coord coords;
	private Calendar lastTimestamp;
	
	public enum VesselType {
		BOAT
		// XXX Add all supported types here
	}
	
	public Vessel(String id, VesselType type) {
		this.id = id;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public VesselType getType() {
		return type;
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
		return lastTimestamp;
	}
	
	public void update(Coord newCoords, Course newCourse, Calendar timestamp) {
		course = newCourse;
		coords = newCoords;
		lastTimestamp = timestamp;
	}
	
	public void update(UpdateData data) throws Exception {
		if(id.equals(data.Id) && type == data.Type){
			course = data.Course;
			coords = data.Coordinates;
			lastTimestamp = data.Timestamp;
		
		}else{
			throw new Exception("Not the correct vessel ID and type");
		}
	}
	
	public UpdateData getUpdateData(Calendar timestamp) {
		return new UpdateData(id, type, getCoord(timestamp), getCourse(timestamp), timestamp);
	}
}
