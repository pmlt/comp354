// Manipulation of the vessel information
// is made here
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class VMS {
	private final int maxBoat = 100;
	private Vessel[] boat = new Vessel[maxBoat];
//	private boolean[] active = new boolean[maxBoat];
	private double[][] distance = new double[maxBoat][maxBoat];
	private boolean[] highRisk = new boolean[maxBoat];
	private boolean[] lowRisk = new boolean[maxBoat];
//	private int[] orderAsc = new int[maxBoat];
	private int count;
	private int time;
	private int range;
	private int starttime;
	private String vsf;
	private int timestep;
	VMS() {
		vsf = "000";
		starttime = 
				timestep = 
				range = 
				time = 
				count = 0;
	}
	void ini() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(
							new File("C:/Users/kentaurus/Documents/GitHub/comp354/src/src/comp354_vessel.txt")));
//							new File("comp354_vessel.vsf")));
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {

				String[] a = line.split("\\s+");
				
				if (a[0].trim().compareToIgnoreCase("VSF") == 0)
					vsf = a[1];

				else if (a[0].trim().compareToIgnoreCase("STARTTIME") == 0) {
//					System.out.println(a[2]);
					starttime = Integer.parseInt(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("TIMESTEP") == 0) {
//					System.out.println(a[2]);
					timestep = Integer.parseInt(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("TIME") == 0) {
//					System.out.println(a[3]);
					time = Integer.parseInt(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("RANGE") == 0) {
//					System.out.println(a[2]);
					range = Integer.parseInt(a[1]);
				}
				
				else if (a[0].trim().compareToIgnoreCase("NEWT") == 0) {
//					Integer.parseInt(word(3, endStep(4, inputLength, line), line));
					boat[count++] = new Vessel(a[1], 
							Integer.parseInt(a[2]),
							Double.parseDouble(a[3]),
							Double.parseDouble(a[4]),
							Double.parseDouble(a[5]),
							Double.parseDouble(a[6]));
				}
				
				
			} // END OF WHILE LOOP
			bufferedReader.close();
		} // END IF TRY
		catch (Exception e) {        e.printStackTrace();	}
		
		updateBoats();
		updateDistance();
	}
	
	void update() {
		for (int i = 0; i<count; i++)
			boat[i].setNextPosition();
		updateBoats();
		updateDistance();
	}
	
	private void updateBoats() {
		for (int i=0; i<count; i++)
			boat[i].setNextPosition();
	}
		
	private void updateDistance()  {
		for (int i=0; i< count; i++)
			for (int j=0; j<count; j++) {
				if (i == j)		distance[i][j] = 0;
				else {
					double deltaX = boat[i].getXPosition() - boat[j].getXPosition();
					double deltaY = boat[i].getYPosition() - boat[j].getYPosition();
					double deltaD = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
					
					distance[i][j] = deltaD;
					distance[j][i] = deltaD;
					
					if (deltaD < 50.0) {
						highRisk[i] = true;
						highRisk[j] = true;
					}
					else if (deltaD < 100.0) {
						lowRisk[i] = true;
						lowRisk[j] = true;
						
					}
					else {
						lowRisk[i] = false;
						lowRisk[j] = false;
						highRisk[i] = false;
						highRisk[j] = false;
					}
				}// END OF ELSE
			}// END OF FOR LOOP
		
	}
	
	int getCount() 				{	return count; }
	String getVSF()				{	return vsf; }
	String getVesselId(int i)	{	return boat[i].getVesselId(); }
	double getXPosition(int i)	{	return Math.floor(boat[i].getXPosition() * 1e5) / 1e5; }
	double getYPosition(int i)	{	return Math.floor(boat[i].getYPosition() * 1e5) / 1e5; }
//	double getXVelocity(int i)	{	return boat[i].getXVelocity(); }
//	double getYVelocity(int i)	{	return boat[i].getYVelocity(); }
	double getSpeed(int i)		{	return Math.floor(boat[i].getSpeed() * 1e5) / 1e5; }
	boolean getHighRisk(int i)	{	return highRisk[i]; }
	boolean getLowRisk(int i)	{	return lowRisk[i]; }
	int getType(int i)			{	return boat[i].getType(); }
	int getStartTime()			{	return starttime; }
	int getTime()				{	return time; }
	int getRange()				{	return range; }
	int getTimeStep()			{	return timestep; }
} // END IF ,java

