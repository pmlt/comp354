package common;

import java.util.*;


public class Vessel {
	private VesselType type;
	private String id;
	private Course course;
	private Coord coords;
	private Calendar lastTimestamp;
	
	public enum VesselType {
		SWIMMER(1), 
		SPEED_BOAT(2), 
		FISHING_BOAT(3), 
		CARGO_BOAT(4), 
		PASSENGER_VESSEL(5),
		UNKNOWN(6);
		
		private int value;
		private VesselType(int v) { value = v; }
		public int getValue() { return value; }
		public String toString() {
			switch (value) {
			case 1: return "Human (swimmer)";
			case 2: return "Speed Boat";
			case 3: return "Fishing Boat";
			case 4: return "Cargo Vessel";
			case 5: return "Passenger Vessel";
			case 6: return "Unknown";
			default: return "Unknown";
			}
		}
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
		time = time / 1000; //Convert to seconds
		
		if (time > 0){
			double x = (double)(coords.x() + course.xVel()*time);
			double y = (double)(coords.y() + course.yVel()*time);
			return new Coord(x, y);
		}
		
		else if(time < 0) {
			throw new IllegalStateException("Trying to read an old timestamp");
		}
		return coords;
	}
	
	public Course getCourse(Calendar timestamp) throws IllegalStateException {
		if (timestamp.before(lastTimestamp))
			throw new IllegalStateException("Trying to read an old timestamp");
		
		return course;
	}
	
	public String getCourseInDegrees(Calendar timestamp) {
		double vx = getCourse(timestamp).xVel();
		double vy = getCourse(timestamp).yVel();
		if (Math.abs(vx) < 0.00001 && Math.abs(vy) < 0.00001)
			return "Stationary";
		else if (Math.abs(vx) < 0.00001) {
			if (vy > 0)
				return "0" + "\u00B0";
			else
				return "180" + "\u00B0";
		}
		else if (Math.abs(vy) < 0.00001) {
			if (vx > 0)
				return "90" + "\u00B0";
			else
				return "270" + "\u00B0";
		}
		else if (vx > 0 && vy > 0)
			return Long.toString(Math.round(Math.toDegrees(Math.atan(Math.abs(vx/vy))))) + "\u00B0";
		else if (vx > 0 && vy < 0)
			return Long.toString(Math.round(90 + Math.toDegrees(Math.atan(Math.abs(vx/vy))))) + "\u00B0";
		else if (vx < 0 && vy < 0)
			return Long.toString(Math.round(180 + Math.toDegrees(Math.atan(Math.abs(vx/vy))))) + "\u00B0";
		else
			return Long.toString(Math.round(270 + Math.toDegrees(Math.atan(Math.abs(vx/vy))))) + "\u00B0";	
	}
	
	public double getSpeed(Calendar timestamp) throws IllegalStateException {
		Course c = getCourse(timestamp);
		double precision = Math.sqrt(Math.pow(c.xVel(), 2.0) + Math.pow(c.yVel(), 2.0));
		return Math.floor(precision * 1e5) / 1e5;
	}
	
	public double getDistance(Calendar timestamp) throws IllegalStateException {
		Coord now = getCoord(timestamp);
		double deltaX = now.x();
		double deltaY = now.y();
		double precision = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
		return Math.floor(precision * 1e5) / 1e5;
	}
	
	public Calendar getLastTimestamp() {
		return lastTimestamp;
	}
	
	public void update(Coord newCoords, Course newCourse, Calendar timestamp) throws IllegalStateException {
		if (timestamp.before(lastTimestamp)) 
			throw new IllegalStateException("Cannot update time before last timestamp!");
		if (timestamp.equals(lastTimestamp))
			throw new IllegalStateException("Cannot re-update last timestamp!");
		//Very important..! Copy the timestamp in case it gets modified somewhere else
		course = (Course)newCourse.clone();
		coords = (Coord)newCoords.clone();
		lastTimestamp = (Calendar)timestamp.clone();
	}
	
	public void update(UpdateData data) throws IllegalStateException {
		if(id.equals(data.Id) && type == data.Type){
			update(data.Coordinates, data.Course, data.Timestamp);
		}else{
			throw new IllegalStateException("Not the correct vessel ID and type");
		}
	}
	
	public UpdateData getUpdateData(Calendar timestamp) throws IllegalStateException {
		
		Coord tempCoords = new Coord(0, 0);
		Course tempCourse = new Course(0,0);
		
		tempCoords = getCoord(timestamp);
		tempCourse = getCourse(timestamp);
		
		return new UpdateData(id, type, tempCoords, tempCourse, timestamp);
	}
	
	public boolean equals(Object other){
		if (other == null) return false;
	    if (!(other instanceof Vessel))return false;
	    
		Vessel vOther = (Vessel)other;
		return id.equals(vOther.getId()) && type == vOther.getType();
	}
}
