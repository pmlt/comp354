import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RadarSimulator {
	private HashMap<String,Vessel> boats; //String key is vessel ID
	private ArrayDeque<Vessel> queue;
	private PriorityQueue<Vessel> timedQueue;
	private final int MAX_BOATS = 100;
	private final double MIN_TIMESTEP = 0.5;
	private File inputFile;
	private double time;
	private int range;
	private double starttime;
	private String vsf;
	private double timestep;
	private double timeCounter;
	
	RadarSimulator() {
		boats = new HashMap<String,Vessel>(MAX_BOATS);
		queue = new ArrayDeque<Vessel>();
		timedQueue = new PriorityQueue<Vessel>();
		timeCounter = 0;
		inputFile = new File("src/simulator/comp354_vessel.vsf");
		ini();
	}
	
	void ini() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] a = line.split("\\s+");
				if (a[0].trim().compareToIgnoreCase("VSF") == 0)
					vsf = a[1];
				else if (a[0].trim().compareToIgnoreCase("STARTTIME") == 0)
					starttime = Double.parseDouble(a[1]);
				else if (a[0].trim().compareToIgnoreCase("TIMESTEP") == 0) {
					timestep = Double.parseDouble(a[1]);
					if (timestep < MIN_TIMESTEP) {
						timestep = MIN_TIMESTEP;
					}
				}
				else if (a[0].trim().compareToIgnoreCase("TIME") == 0)
					time = Double.parseDouble(a[1]);
				else if (a[0].trim().compareToIgnoreCase("RANGE") == 0)
					range = Integer.parseInt(a[1]);
				else if (a[0].trim().compareToIgnoreCase("NEWT") == 0 && Double.parseDouble(a[7]) == 0)
					addVessel(new Vessel(a[1],
							Integer.parseInt(a[2]),
							Double.parseDouble(a[3]),
							Double.parseDouble(a[4]),
							Double.parseDouble(a[5]),
							Double.parseDouble(a[6]),
							Double.parseDouble(a[7])));
				else if (a[0].trim().compareToIgnoreCase("NEWT") == 0 && Double.parseDouble(a[7]) > 0) {
					timedQueue.add(new Vessel(a[1],
							Integer.parseInt(a[2]),
							Double.parseDouble(a[3]),
							Double.parseDouble(a[4]),
							Double.parseDouble(a[5]),
							Double.parseDouble(a[6]),
							Double.parseDouble(a[7])));
				}
			} // END OF WHILE LOOP
			bufferedReader.close();
		} // END OF TRY
		catch (Exception e) {	e.printStackTrace();	}
	}
	
	public void updateBoats() {
		for (Vessel v : boats.values()) {
			v.setNextPosition(v.getXPosition() + v.getXVelocity()*timestep,
					v.getYPosition() + v.getYVelocity()*timestep);
		}
		for (Vessel v : queue) {
			v.setNextPosition(v.getXPosition() + v.getXVelocity()*timestep,
					v.getYPosition() + v.getYVelocity()*timestep);
		}
		// Removing vessels that went out of range on the radar
		String[] ids = new String[MAX_BOATS];
		int idCount = 0;
		for (Vessel v : boats.values()) {
			if (calculateDistance(v) > range) {
				ids[idCount] = v.getVesselId();
				idCount++;
			}
		}
		for (int i=0; i<idCount; i++)
			boats.remove(ids[i].toString());
		// Removing vessels that went out of range while in the queue
		ArrayDeque<Vessel> newQueue = new ArrayDeque<Vessel>();
		for (Vessel v : queue) {
			if (calculateDistance(v) <= range)
				newQueue.add(v);
		}
		queue = newQueue;
		// Transferring vessels from queue to radar if there is room
		int queueCounter = 0;
		for (Vessel v : queue) {
			if (boats.size() >= MAX_BOATS)
				break;
			else {
				boats.put(v.getVesselId(), v);
				queueCounter++;
			}
		}
		for (int i=0; i<queueCounter; i++)
			queue.remove();
		// Adding vessels from timed queue when start time is reached
		while (timedQueue.size() > 0 && timedQueue.peek().getStartTime() <= timeCounter)
			addVessel(timedQueue.remove());
		
		timeCounter += timestep;
	}
	
	final boolean removeVessel(String vesselId) {
		if (boats.containsKey(vesselId)) {
			boats.remove(vesselId);
			return true;
		}
		else
			return false;
	}
	
	void addVessel(Vessel ve) {
		if (boats.size() > MAX_BOATS) {
			queue.add(ve);
		}
		else 
			boats.put(ve.getVesselId(), ve);
	}
	
	double calculateDistance(Vessel ve) {
		return Math.sqrt(Math.pow(ve.getXPosition(), 2) + Math.pow(ve.getYPosition(), 2));
	}
	
	double calculateSpeed(Vessel ve) {
		return Math.sqrt(Math.pow(ve.getXVelocity(), 2) + Math.pow(ve.getYVelocity(), 2));
	}
	
	String calculateCourse(Vessel ve) {
		double vx = ve.getXVelocity();
		double vy = ve.getYVelocity();
		if (Math.abs(vx) < 0.00001 && Math.abs(vy) < 0.00001)
			return "Stationary";
		else if (Math.abs(vy) < 0.00001) { // Just to avoid division by zero
			if (ve.getXVelocity() > 0)
				return "90" + "\u00B0";
			else
				return "270" + "\u00B0";
		}
		else if (vx > 0 && vy > 0)
			return Long.toString(Math.round(Math.toDegrees(Math.atan(vx/vy)))) + "\u00B0";
		else if (vx > 0 && vy < 0)
			return Long.toString(Math.round(90 + Math.toDegrees(Math.atan(vx/vy)))) + "\u00B0";
		else if (vx < 0 && vy < 0)
			return Long.toString(Math.round(180 + Math.toDegrees(Math.atan(vx/vy)))) + "\u00B0";
		else
			return Long.toString(Math.round(270 + Math.toDegrees(Math.atan(vx/vy)))) + "\u00B0";
	}

	final int getCount() 				{	return boats.size();	}
	final String getVSF()				{	return vsf; 		}
	final double getStartTime()			{	return starttime; 	}
	final double getTime()				{	return time; 		}
	final int getRange()				{	return range; 		}
	final double getTimeStep()			{	return timestep; 	}
	final double getTimeCounter()		{	return timeCounter;	}
	final boolean timeExpired()			{	return timeCounter > time;	}
	final Collection<Vessel> getVessels()	{	return boats.values();	}
	
}
