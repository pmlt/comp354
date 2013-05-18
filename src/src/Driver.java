// Preliminary testing will happen here
import java.util.Scanner; 

public class Driver {
	public static void main(String[] args) {
		Scanner readUserInput=new Scanner(System.in);
		
		VMS v = new VMS();
		v.ini();
		System.out.println("VSF:\t\t" + v.getVSF());
		System.out.println("STARTTIME:\t" + v.getStartTime());
		System.out.println("TIMSTEP:\t" + v.getTimeStep());
		System.out.println("TIME:\t\t" + v.getTime());
		System.out.println("RANGE:\t\t" + v.getRange());
		System.out.println();
		System.out.println("OUTPUT FOR VESSELS");
		System.out.println("Press Enter to get next interation");
		System.out.println("Press e then Enter to exit");

		boolean c = true;
		while (c) {
			System.out.println();
			System.out.println("VESSEL ID \t X POSITION \t Y POSITION");

			for (int i=0; i<v.getCount(); i++) {
				System.out.println(v.getVesselId(i) + "\t\t  " + v.getXPosition(i) + "\t" + v.getYPosition(i));
			}
			String exit = readUserInput.nextLine();
			if (exit.compareToIgnoreCase("e") == 0)
				c = false;
			v.update();
		}

	}
}
