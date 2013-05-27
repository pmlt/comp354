// Preliminary testing will happen here
import java.awt.BorderLayout;
import javax.swing.JFrame;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTabbedPane;
public class Driver {
	static void GUITesting(VMS v, RadarSimulator rs) {
		final String VIEW[] = { "Table view", "Map view"};
		JFrame f = new JFrame("TESTING VMS");		
		f.setSize(670,560);
		
		FilterPanel fp = new FilterPanel();
		TablePanel tp = new TablePanel(v.filterData(rs, fp.getFilter()), rs);
		MapPanel mp = new MapPanel(rs);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.add(tp, VIEW[0]);
		tabbedPane.add(mp,VIEW[1]);
		f.setJMenuBar(new MenuBar());
		f.add(fp, BorderLayout.WEST);
		f.add(tabbedPane, BorderLayout.CENTER);
		
/*		f.setSize(500,250);
		f.setLayout(new GridLayout(1,1));
		f.setJMenuBar(new MenuBar());
		f.add(fp);
		f.add(tp);
*/		
		Timer timer = new Timer("Update");		
		UpdateTask ut = new UpdateTask(v, tp, fp, f, rs);
		timer.schedule(ut, rs.getStartTime(), rs.getTimeStep()*1000);// (FUNCTION, START, END)
		
		f.setVisible(true);
	}
	public static void main(String[] args) {		
		VMS v = new VMS();
		RadarSimulator rs = new RadarSimulator();
		rs.ini();
//		v.ini();
		GUITesting(v, rs);
	}
}

class UpdateTask extends TimerTask {
    //times member represent calling times.
    private int times = 0;
    private VMS v;
    private JFrame f;
    private TablePanel tp;
    private FilterPanel fp;
    private RadarSimulator rs;

    UpdateTask(VMS v, TablePanel tp, FilterPanel fp, JFrame f, RadarSimulator rs) {
    	this.v = v;
    	this.f = f;
    	this.tp = tp;
    	this.fp = fp;
    	this.rs = rs;
    }

    public void run() {
//		System.out.println(times);
    	times++;
//		if (times == v.getTimeStep()) {
    	rs.updateBoats();
    	v.update(rs);
    	if (fp.getScreenStatusUpdate()) {
    		tp.update(v.filterData(rs, fp.getFilter()));
    		fp.update();
    	}
    	f.repaint();
//    	System.out.println("Filter still set to " + fp.getFilter());
//		times = 0;
//		}
//		System.out.println("HERE");
        if (times > rs.getTime()/rs.getTimeStep())
        	this.cancel();
    }

}

