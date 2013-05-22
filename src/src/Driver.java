// Preliminary testing will happen here
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.Scanner; 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Timer;
import java.util.TimerTask;

public class Driver {
	public static void main(String[] args) {
		
		VMS v = new VMS();
		v.ini();
		
//		consoleTesting(v);
		GUITesting(v);
	}
	static void GUITesting(VMS v) {
		final String VIEW[] = { "Table view", "Map view"};
		JFrame f = new JFrame("TESTING VMS");		
		f.setSize(500,250);


		
		TablePanel tp = new TablePanel(v);
		
		// This is just to check if it actually switched panels
		// To be replaced later by the map
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
		
		JPanel cards = new JPanel(new CardLayout());
        cards.add(tp, VIEW[0]);
        cards.add(card2,VIEW[1]);
		f.setJMenuBar(new MenuBar(cards, VIEW));
		f.add(cards);
				
		
//		Testing demo = new Testing(v, tp);
//       demo.addComponentToPane(f.getContentPane());
        

		Timer timer = new Timer("Update");		
		UpdateTask ut = new UpdateTask(v, tp, f); 
		timer.schedule(ut, v.getStartTime(), v.getTimeStep()*1000);// (FUNCTION, START, END)

		f.setVisible(true);
	}
	
	static void consoleTesting(VMS v) {
		Scanner readUserInput=new Scanner(System.in);

		// Used for console testing
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
				readUserInput.close();
	}
}

class UpdateTask extends TimerTask {
    //times member represent calling times.
    private int times = 0;
    private VMS v;
    private JFrame f;
    private TablePanel tp;
    UpdateTask(VMS v, TablePanel tp, JFrame f) {
    	this.v = v;
    	this.f = f;
    	this.tp = tp;
    }
    
    public void run() {
//		System.out.println(times);
        times++;
//		if (times == v.getTimeStep()) {
        	v.update();
        	tp.update(v);
        	f.repaint();
//		times = 0;
//		}
//		System.out.println("HERE");
        if (times > v.getTime()/v.getTimeStep())
        	this.cancel();
    }

}

