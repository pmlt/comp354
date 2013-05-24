import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RadarSimulator {
	private final int maxBoat = 100;
	private Vessel[] boat;
	private int count;
	private int time;
	private int range;
	private int starttime;
	private String vsf;
	private int timestep;
	private Object[][] data;
	RadarSimulator() {
		vsf = "000";
		starttime = 
				timestep = 
				range = 
				time = 
				count = 0;
		boat = new Vessel[maxBoat];
		data = new Object[maxBoat][8];
	}
	void ini() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(
							new File("C:/Users/kentaurus/Documents/GitHub/comp354/src/src/comp354_vessel.txt")));
//							new File("/Users/Ghislain/Documents/Assignments/Comp354/project/comp354/src/src/comp354_vessel.txt")));
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
	}
	public void updateBoats() {
		for (int i=0; i<getCount(); i++) {
			boat[i].setNextPosition(boat[i].getXPosition() + boat[i].getXVelocity()*timestep,
					boat[i].getYPosition() + boat[i].getYVelocity()*timestep);
			data[i][2] = boat[i].getXPosition();
			data[i][3] = boat[i].getYPosition();
		}
	}
	final int getCount() 				{	return count; 		}
	final String getVSF()				{	return vsf; 		}
	final int getStartTime()			{	return starttime; 	}
	final int getTime()					{	return time; 		}
	final int getRange()				{	return range; 		}
	final int getTimeStep()				{	return timestep; 	}
	final Object[][] getData()			{ 	return data; 		}
	final double getXPosBoat(int i)		{	return boat[i].getXPosition();	}
	final double getYPosBoat(int i)		{	return boat[i].getYPosition();	}
	final Object[] getRow(int i)		{	return data[i];		}
}