package simulator;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import common.Vessel;

public class SimulatorConfiguration {
	private int _StartDelay;
	private int _TimeInterval;
	private int _TotalTime;
	private int _RadarRange;
	
	List<Vessel> _Vessels;
	
	// Force users to create configuration from VSF file
	private SimulatorConfiguration() {
		_Vessels = new ArrayList<Vessel>();
	}

	public int getStartDelay() {
		return _StartDelay;
	}
	public void setStartDelay(int _StartDelay) {
		this._StartDelay = _StartDelay;
	}
	public int getTimeInterval() {
		return _TimeInterval;
	}
	public void setTimeInterval(int _TimeInterval) {
		this._TimeInterval = _TimeInterval;
	}
	public int getTotalTime() {
		return _TotalTime;
	}
	public void setTotalTime(int _TotalTime) {
		this._TotalTime = _TotalTime;
	}
	public int getRadarRange() {
		return _RadarRange;
	}
	public void setRadarRange(int _RadarRange) {
		this._RadarRange = _RadarRange;
	}
	
	public List<Vessel> getVessels() {
		return _Vessels;
	}
	public void addVessel(Vessel v) {
		_Vessels.add(v);
	}
	
	/*
	 * Parse a VSF file to read configuration.
	 * Should throw ParseException whenever it encounters an unexpected
	 * format in the input file.
	 */
	public static SimulatorConfiguration parseVSF(InputStream in) 
			throws IOException, ParseException {
		SimulatorConfiguration sc = new SimulatorConfiguration();
		// XXX TODO by Pinsson; set properties of sc by reading file
		return sc;
	}
}
