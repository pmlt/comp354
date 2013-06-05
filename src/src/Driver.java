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
		f.setSize(700,600);
		
		FilterPanel fp = new FilterPanel();
		TablePanel tp = new TablePanel(v.filterData(fp.getFilter()), rs, v);
		MapPanel mp = new MapPanel(v, rs, fp);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.add(tp, VIEW[0]);
		tabbedPane.add(mp,VIEW[1]);
		f.setJMenuBar(new MenuBar());
		f.add(fp, BorderLayout.WEST);
		f.add(tabbedPane, BorderLayout.CENTER);
				
		Timer timer = new Timer("Update");		
		UpdateTask ut = new UpdateTask(v, tp, fp, f, rs);
		timer.schedule(ut, (int)(rs.getStartTime()*1000), (int)(rs.getTimeStep()*1000)); // (FUNCTION, DELAY, PERIOD)
		
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		RadarSimulator rs = new RadarSimulator();
		VMS v = new VMS(rs);
		GUITesting(v, rs);
	}
}

class UpdateTask extends TimerTask {
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
		rs.updateBoats();
		v.update();
		tp.update(v.filterData(fp.getFilter()));
		f.repaint();
		if (rs.timeExpired())
			this.cancel();
	}
}
