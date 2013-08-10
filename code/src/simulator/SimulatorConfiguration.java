package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import common.Coord;
import common.Course;
import common.Vessel;
import common.Vessel.VesselType;
public class SimulatorConfiguration {
	private int _StartDelay;
	private double _TimeInterval;
	private int _TotalTime;
	private int _RadarRange;
	private String _Version;
	
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
	public double getTimeInterval() {
		return _TimeInterval;
	}
	public void setTimeInterval(double _TimeInterval) {
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
	public String getVersion() {
		return _Version;
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
	public static SimulatorConfiguration parseVSF(BufferedReader in, Calendar reftime) 
			throws IOException, ParseException {
		SimulatorConfiguration sc = new SimulatorConfiguration();
		String line;
		while ((line = in.readLine()) != null) {
			String[] a = line.split("\\s+");
			if (a[0].trim().compareToIgnoreCase("VSF") == 0)
				sc._Version = a[1];
			else if (a[0].trim().compareToIgnoreCase("STARTTIME") == 0)
				sc.setStartDelay((int) Double.parseDouble(a[1]));
			else if (a[0].trim().compareToIgnoreCase("TIMESTEP") == 0) {
				sc.setTimeInterval(Double.parseDouble(a[1]));
			}
			else if (a[0].trim().compareToIgnoreCase("TIME") == 0)
				sc.setTotalTime((int) Double.parseDouble(a[1]));
			else if (a[0].trim().compareToIgnoreCase("RANGE") == 0)
				sc.setRadarRange(Integer.parseInt(a[1]));
			else if (a[0].trim().compareToIgnoreCase("NEWT") == 0 && Double.parseDouble(a[7]) == 0) {
				Vessel v = null;
				if (Integer.parseInt(a[2])-1 >= 0 && Integer.parseInt(a[2])-1 < 5)
					v = new Vessel(a[1], VesselType.values()[Integer.parseInt(a[2])-1]);
				else
					v = new Vessel(a[1], VesselType.values()[5]);
				v.update(new Coord(Double.parseDouble(a[3]), Double.parseDouble(a[4])),
						new Course(Double.parseDouble(a[5]), Double.parseDouble(a[6])),
						reftime);
				sc.addVessel(v);
			}
			else if (a[0].trim().compareToIgnoreCase("NEWT") == 0 && Double.parseDouble(a[7]) > 0) {
				Vessel v = null;
				if (Integer.parseInt(a[2])-1 >= 0 && Integer.parseInt(a[2])-1 < 5)
					v = new Vessel(a[1], VesselType.values()[Integer.parseInt(a[2])-1]);
				else
					v = new Vessel(a[1], VesselType.values()[5]);
				v.update(new Coord(Double.parseDouble(a[3]), Double.parseDouble(a[4])),
						new Course(Double.parseDouble(a[5]), Double.parseDouble(a[6])),
						reftime);
				sc.addVessel(v);
			}
			
		} // END OF WHILE LOOP
		
		// Check that data was correct.
		if (!"001".equals(sc._Version) || 
			sc._RadarRange < 0 ||
			sc._StartDelay < 0 ||
			sc._TimeInterval < 0.5 ||
			sc._TotalTime < (sc._TimeInterval) ||
			sc._Vessels.size() <= 0) {
			throw new ParseException("VSF data incomplete or incorrect!", 0);
		}
		return sc;
	}
}
