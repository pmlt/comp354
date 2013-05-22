// Preliminary testing will happen here
import java.awt.BorderLayout;
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
		f.setSize(520,560);
		
		FilterPanel fp = new FilterPanel();
		TablePanel tp = new TablePanel(v);
		MapPanel mp = new MapPanel(v);
		
		JPanel cards = new JPanel(new CardLayout());
        cards.add(tp, VIEW[0]);
        cards.add(mp,VIEW[1]);
		f.setJMenuBar(new MenuBar(cards, VIEW));
		f.add(fp, BorderLayout.WEST);
		f.add(cards, BorderLayout.CENTER);
		
/*		f.setSize(500,250);
		f.setLayout(new GridLayout(1,1));
		f.setJMenuBar(new MenuBar());
		f.add(fp);
		f.add(tp);
*/		
		Timer timer = new Timer("Update");		
		UpdateTask ut = new UpdateTask(v, tp, fp, f);
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
    private FilterPanel fp;
    
    UpdateTask(VMS v, TablePanel tp, FilterPanel fp, JFrame f) {
    	this.v = v;
    	this.f = f;
    	this.tp = tp;
    	this.fp = fp;
    }

    public void run() {
//		System.out.println(times);
    	times++;
//		if (times == v.getTimeStep()) {
    	v.update();
//    	tp.update(v, fp.getFilter());
    	f.repaint();
    	System.out.println("Filter still set to " + fp.getFilter());
//		times = 0;
//		}
//		System.out.println("HERE");
        if (times > v.getTime()/v.getTimeStep())
        	this.cancel();
    }

}

