import java.util.TimerTask;

import javax.swing.JFrame;

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
