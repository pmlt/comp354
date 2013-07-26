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
	
	//illegal state exception
	public Coord getCoord(Calendar timestamp) throws IllegalStateException {
		long time = timestamp.getTimeInMillis() - lastTimestamp.getTimeInMillis();
		
		if (time > 0){
			int x = (int)(coords.x() + course.xVel()*time);
			int y = (int)(coords.y() + course.yVel()*time);
			coords = new Coord(x, y);
		}
		
		else if(time < 0) {
			throw new IllegalStateException("Trying to read an old timestamp");
		}
		
		return coords;
	}
	
	public Course getCourse(Calendar timestamp) throws IllegalStateException {		
		// XXX Will the vessel's speed really get bigger overtime? I'm not sure this is necessary
		
		long time = timestamp.getTimeInMillis()  - lastTimestamp.getTimeInMillis();
		
		if (time > 0){
			int xVel = (int)(course.xVel() + course.xVel()*time);
			int yVel = (int)(course.yVel() + course.yVel()*time);
			course = new Course(xVel, yVel);
		}
		
		else if(time < 0) {
			throw new IllegalStateException("Trying to read an old timestamp");
		}
		
		return course;
	}
	
	public Calendar getLastTimestamp() {
		return lastTimestamp;
	}
	
	public void update(Coord newCoords, Course newCourse, Calendar timestamp) {
		course = newCourse;
		coords = newCoords;
		lastTimestamp = timestamp;
	}
	
	public void update(UpdateData data) throws IllegalStateException {
		if(id.equals(data.Id) && type == data.Type){
			course = data.Course;
			coords = data.Coordinates;
			lastTimestamp = data.Timestamp;
		
		}else{
			throw new IllegalStateException("Not the correct vessel ID and type");
		}
	}
	
	public UpdateData getUpdateData(Calendar timestamp) {
		
		Coord tempCoords = new Coord(0, 0);
		Course tempCourse = new Course(0,0);
		
		try{
			tempCoords = getCoord(timestamp);
			tempCourse = getCourse(timestamp);
		}catch(IllegalStateException e){
			System.out.println("Invalid timestamp");
		}
		
		return new UpdateData(id, type, tempCoords, tempCourse, timestamp);
	}
}
