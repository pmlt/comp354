package vms;

import java.util.*;

import vms.ConnectionServer.Observer;

import common.UpdateData;
import common.Vessel;


public class RadarMonitor implements ConnectionServer.Observer {
	
	private Coord lowerRange;
	private Coord upperRange;
	private ArrayList<Vessel> _Vessels;
	private List<Observer> _Observers;
	
	public interface Observer {
		public void refresh(List<Alert> alerts);
	}
	
	public void setRange(Coord lowerRange, Coord upperRange) {
		this.lowerRange = lowerRange;
		this.upperRange = upperRange;
	}
	
	public int getVesselCount() {
		return _Vessels.size();
	}
	
	public List<Vessel> getVessels() {
		// XXX Do we want to clone this before returning it?
		return _Vessels;
	}
	
	public void registerObserver(Observer o) {
		if (!_Observers.contains(o)) _Observers.add(o);
	}
	
	public void unregisterObserver(Observer o) {
		_Observers.remove(o);
	}

	@Override
	public void update(UpdateData data) {
		// XXX Update internal list with new incoming data, then call refresh()
		
	}

	@Override
	public void refresh(Calendar timestamp) {
		// XXX Simply notify observers to refresh UI
		// Must check for alert status, and notify observers accordingly
	}

}
