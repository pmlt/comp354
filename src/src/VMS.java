// Manipulation of the vessel information
// is made here
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class VMS {
	private final int maxBoat = 100;
	private Vessel[] boat;
	private double[][] distance;
	private boolean[] highRisk;
	private boolean[] lowRisk;
	private int count;
	private int time;
	private int range;
	private int starttime;
	private String vsf;
	private int timestep;
	private Object[][] data;

	VMS() {
		vsf = "000";
		starttime = 
				timestep = 
				range = 
				time = 
				count = 0;
		boat = new Vessel[maxBoat];
		distance = new double[maxBoat][maxBoat];
		highRisk = new boolean[maxBoat];
		lowRisk  = new boolean[maxBoat];
		data = new Object[maxBoat][8];
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
					data[count][4] = boat[count].getSpeed();
					data[count][5] = null;
					data[count][6] = null;
					data[count][7] = null;
					count++;
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
		for (int i=0; i<count; i++) {
			boat[i].setNextPosition();
			data[i][2] = boat[i].getXPosition();
			data[i][3] = boat[i].getYPosition();
		}
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
	
	final int getCount() 				{	return count; }
	final String getVSF()				{	return vsf; }
	final int getStartTime()			{	return starttime; }
	final int getTime()					{	return time; }
	final int getRange()				{	return range; }
	final int getTimeStep()				{	return timestep; }
	final String getVesselId(int i)		{	return boat[i].getVesselId(); }
	final double getXPosition(int i)	{	return Math.floor(boat[i].getXPosition() * 1e5) / 1e5; }
	final double getYPosition(int i)	{	return Math.floor(boat[i].getYPosition() * 1e5) / 1e5; }
	final double getSpeed(int i)		{	return Math.floor(boat[i].getSpeed() * 1e5) / 1e5; }
	final boolean getHighRisk(int i)	{	return highRisk[i]; }
	final boolean getLowRisk(int i)		{	return lowRisk[i]; }
	final int getType(int i)			{	return boat[i].getType(); }
	final Object[][] getData()			{ 	return data; }
} // END IF ,java

