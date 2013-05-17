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
	VMS() {
		range = time = count = 0;
		
	}
	void ini() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(
							new File("cino354_vessel.vsf")));
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] splitArray = line.split(" ");
				
				// If first string is "//" ignore line
				if (splitArray[0].compareToIgnoreCase("//") == 0) {}
				
				// If first element is TIME it will set the timer in which it will repeat the update
				else if (splitArray[0].compareToIgnoreCase("time") == 0) { time = Integer.parseInt(splitArray[1]); }
				
				// If first element is RANGE then it will determine the default scale of the map
				else if (splitArray[0].compareToIgnoreCase("range") == 0) { range = Integer.parseInt(splitArray[1]); }
				
				// If first element is NEWT then it will insert the information
				else if (splitArray[0].compareToIgnoreCase("NEWT") == 0) { 
					boat[count++] = new Vessel(	Integer.parseInt(splitArray[1]),		// Vessel ID 
												Integer.parseInt(splitArray[2]), 							// Type
												Double.parseDouble(splitArray[3]), 		// xPos
												Double.parseDouble(splitArray[4]), 		// yPos
												Double.parseDouble(splitArray[5]), 		// xVel
												Double.parseDouble(splitArray[6]) );	// yVel
				} // END OF ELSE IF
				
				
			} // END OF WHILE LOOP
			bufferedReader.close();
		} // END IF TRY
		catch (Exception e) {        e.printStackTrace();	}
		
		update();
	}
	
	void update() {
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
	int getVesselId(int i)		{	return boat[i].getVesselId(); }
	double getXPosition(int i)	{	return boat[i].getXPosition(); }
	double getYPosition(int i)	{	return boat[i].getYPosition(); }
}
