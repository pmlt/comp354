import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RadarSimulator {
	private final int MAX_BOATS = 100;
	private final double MIN_TIMESTEP = 0.5;
	private Vessel[] boat;
	private int count;
	private double time;
	private int range;
	private double starttime;
	private String vsf;
	private double timestep;
	private Object[][] data;
	RadarSimulator() {
		vsf = "000";
		starttime = 
				timestep = 
				time = 0;
		range = 
				count = 0;
		boat = new Vessel[MAX_BOATS];
		data = new Object[MAX_BOATS][9];
	}
	void ini() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(
//							new File("C:/Users/kentaurus/Documents/GitHub/comp354/src/src/comp354_vessel.txt")));
							new File("/Users/Ghislain/Documents/Assignments/Comp354/project/comp354/src/src/comp354_vessel.vsf")));
//							new File("comp354_vessel.vsf")));
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {

				String[] a = line.split("\\s+");
				
				if (a[0].trim().compareToIgnoreCase("VSF") == 0)
					vsf = a[1];

				else if (a[0].trim().compareToIgnoreCase("STARTTIME") == 0) {
//					System.out.println(a[2]);
					starttime = Double.parseDouble(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("TIMESTEP") == 0) {
//					System.out.println(a[2]);
					timestep = Double.parseDouble(a[1]);
					
					if (timestep < MIN_TIMESTEP) {
						System.out.println("Minimum timestep must be 0.5 second!\n"
								+ "Setting timestep to 0.5 second...");
						timestep = 0.5;
					}
				}
				
				else if (a[0].trim().compareToIgnoreCase("TIME") == 0) {
//					System.out.println(a[3]);
					time = Double.parseDouble(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("RANGE") == 0) {
//					System.out.println(a[2]);
					range = Integer.parseInt(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("NEWT") == 0) {
//					Integer.parseInt(word(3, endStep(4, inputLength, line), line));
					if (count > MAX_BOATS) {
						System.out.println("A maximum of 100 vessels can be viewed by the radar.\n" +
								"Further entries will be ignored.");
					}
					else {
						boat[count] = new Vessel(a[1], 
								Integer.parseInt(a[2]),
								Double.parseDouble(a[3]),
								Double.parseDouble(a[4]),
								Double.parseDouble(a[5]),
								Double.parseDouble(a[6]));
						data[count][0] = boat[count].getVesselId();
						data[count][1] = boat[count].getType();
						data[count][2] = boat[count].getXPosition();
						data[count][3] = boat[count].getYPosition();
//						data[count][4] = boat[count].getSpeed();
						data[count][4] = calculateSpeed(boat[count]);
						data[count][5] = calculateCourse(boat[count]);
						data[count][6] = calculateDistance(boat[count]);
						data[count][7] = System.currentTimeMillis();
						data[count][8] = null;
						count++;
					}
				}
				
			} // END OF WHILE LOOP
			bufferedReader.close();
		} // END IF TRY
		catch (Exception e) {        e.printStackTrace();	}
	}
	public void updateBoats() {
		for (int i=0; i<getCount(); i++) {
			boat[i].setNextPosition(boat[i].getXPosition() + boat[i].getXVelocity()*timestep,
					boat[i].getYPosition() + boat[i].getYVelocity()*timestep);
			data[i][2] = boat[i].getXPosition();
			data[i][3] = boat[i].getYPosition();
			data[i][4] = calculateSpeed(boat[i]);
			data[i][5] = calculateCourse(boat[i]);
			data[i][6] = calculateDistance(boat[i]);
			data[i][7] = System.currentTimeMillis();
		}
	}
	
	final boolean removeFromID( Object id ) {
		int index = 0;
		boolean found = false;
		for (int i = 0; i < getCount(); i ++) {
			if (id == data[i][0]) {
				index = i;
				found = true;
			}
		}
		if (!found)
			return found;
		for (int i = index; i<getCount()+1; i++) {
			boat[i] = boat[i+1];
			data[i] = data[i+1];
		}
		count--;
		return found;
	}
	void addVessel(Vessel ve) {
		if (count > MAX_BOATS) {
			System.out.println("A maximum of 100 vessels can be viewed by the radar.\n" +
					"Could not add this vessel.");
			return;
		}
		
		boat[count] = ve;
		data[count][0] = ve.getVesselId();
		data[count][1] = ve.getType();
		data[count][2] = ve.getXPosition();
		data[count][3] = ve.getYPosition();
//		data[count][4] = ve.getSpeed();
		data[count][4] = calculateSpeed(ve);
		data[count][5] = calculateCourse(ve);
		data[count][6] = calculateDistance(ve);
		data[count][7] = System.currentTimeMillis();
		data[count++][8] = null;
		
	}
	
	double calculateSpeed(Vessel ve) {
		return Math.sqrt(Math.pow(ve.getXVelocity(), 2) + Math.pow(ve.getYVelocity(), 2));
	}
	
	String calculateCourse(Vessel ve) {
		double vx = ve.getXVelocity();
		double vy = ve.getYVelocity();
		if (Math.abs(vx) < 0.00001 && Math.abs(vy) < 0.00001)
			return "Stationary";
		else if (Math.abs(vy) < 0.00001) {
			if (ve.getXVelocity() > 0)
				return "90.0" + "\u00B0";
			else
				return "270" + "\u00B0";
		}
		else if (vx > 0 && vy > 0)
			return Double.toString(Math.toDegrees(Math.atan(vx/vy))) + "\u00B0";
		else if (vx > 0 && vy < 0)
			return Double.toString(90 + Math.toDegrees(Math.atan(vx/vy))) + "\u00B0";
		else if (vx < 0 && vy < 0)
			return Double.toString(180 + Math.toDegrees(Math.atan(vx/vy))) + "\u00B0";
		else
			return Double.toString(270 + Math.toDegrees(Math.atan(vx/vy))) + "\u00B0";
	}
	
	double calculateDistance(Vessel ve) {
		return Math.sqrt(Math.pow(ve.getXPosition(), 2) + Math.pow(ve.getYPosition(), 2));
	}
	
	final int getCount() 				{	return count; 		}
	final String getVSF()				{	return vsf; 		}
	final double getStartTime()			{	return starttime; 	}
	final double getTime()				{	return time; 		}
	final int getRange()				{	return range; 		}
	final double getTimeStep()			{	return timestep; 	}
	final Object[][] getData()			{ 	return data; 		}
	final double getXPosBoat(int i)		{	return boat[i].getXPosition();	}
	final double getYPosBoat(int i)		{	return boat[i].getYPosition();	}
	final Object[] getRow(int i)		{	return data[i];		}
	final void addRisk(String[] r)	{
		for (int i = 0; i<getCount();i++) {
			data[i][8] = r[i];
		}
	}
}