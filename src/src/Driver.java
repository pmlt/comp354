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
	static void GUITesting(VMS v) {
		final String VIEW[] = { "Table view", "Map view"};
		JFrame f = new JFrame("TESTING VMS");		
		f.setSize(670,560);
		
		FilterPanel fp = new FilterPanel();
		TablePanel tp = new TablePanel(v, fp.getFilter());
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
	public static void main(String[] args) {		
		VMS v = new VMS();
		v.ini();
		GUITesting(v);
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
    	tp.update(v, fp.getFilter());
    	f.repaint();
//    	System.out.println("Filter still set to " + fp.getFilter());
//		times = 0;
//		}
//		System.out.println("HERE");
        if (times > v.getTime()/v.getTimeStep())
        	this.cancel();
    }

}

