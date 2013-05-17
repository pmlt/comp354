// Preliminary testing will happen here

public class Driver {
	public static void main(String[] args) {
		VMS v = new VMS();
		v.ini();
		System.out.println("VESSEL ID \t X POSITION \t Y POSITION");
		for (int i=0; i<v.getCount(); i++) {
			System.out.println(v.getVesselId(i) + "\t" + v.getXPosition(i)+ "\t" + v.getYPosition(i));
		}

	}
}
