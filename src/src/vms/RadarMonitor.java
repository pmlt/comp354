package vms;

import java.util.*;

import common.Vessel;
import common.Vessel.VesselType;


public class RadarMonitor implements ConnectionServer.Observer {
	public interface Observer {
		public void refresh(List<Alert> alerts);
	}
	
	public void setRange(Coord lowerRange, Coord upperRange) {
		// XXX If ships go beyond this range, we can safely remove them from list
	}
	
	public int getVesselCount() {
		// XXX return how many ships are showing up on the radar right now
		return 0;
	}
	
	public List<Vessel> getVessels() {
		// XXX Return the list of vessels showing up on the radar right now
		return new ArrayList<Vessel>();
	}
	
	public void registerObserver(Observer o) {
		// XXX Add to internal observers list
	}
	
	public void unregisterObserver(Observer o) {
		// XXX Remove from internal observers list
	}

	@Override
	public void update(String id, VesselType type, Coord newCoords,
			Course newCourse, Calendar timestamp) {
		// XXX Update internal list with new incoming data, then call refresh()
		
	}

	@Override
	public void refresh(Calendar timestamp) {
		// XXX Simply notify observers to refresh UI
		// Must check for alert status, and notify observers accordingly
	}

}
