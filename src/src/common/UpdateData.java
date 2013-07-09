package common;

import java.util.Calendar;

import vms.Coord;
import vms.Course;
import vms.Vessel.VesselType;

public class UpdateData {
	public String Id;
	public VesselType Type;
	public Coord Coordinates;
	public Course Course;
	public Calendar Timestamp;
	
	public String toJSON() {
		// XXX TODO by Ghislain
		// JSON specifications: http://json.org/ (there's a thousand libraries to parse JSON out there)
		return "";
	}
	
	public static UpdateData fromJSON(String json) {
		// XXX TODO by Ghislain
		// JSON specifications: http://json.org/ (there's a thousand libraries to parse JSON out there)
		return new UpdateData();
	}
}
