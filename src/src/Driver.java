// Preliminary testing will happen here
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTabbedPane;
//Used for logging in
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Driver {
	static void Loggin() {
		JFrame log = new JFrame("Loggin");
		log.setSize(200, 150);
		log.setResizable(false);
		log.setJMenuBar(new MenuBar());

		//Creating fields
		JLabel label = new JLabel("Enter you password");
//		JLabel op = new JLabel("Operator: op123");
//		JLabel us = new JLabel("User: user123");
		JTextField pass = new JTextField();
		JButton but[] = {new JButton("Login"), new JButton("Exit")};
		JPanel output = new JPanel();
		output.setLayout(new GridLayout(3,1));		
		JPanel commands = new JPanel();
		
		logging l = new logging(log, pass, but);
		// Creating functions
		but[0].addActionListener(l);
		but[1].addActionListener(l);
		// Adding fields to panels
//		output.add(op);
//		output.add(us);
		output.add(label);
		output.add(pass);
		
		commands.add(but[0]);
		commands.add(but[1]);
		// Adding Panels to Frame
		log.add(output, BorderLayout.CENTER);
		log.add(commands, BorderLayout.SOUTH);
		// Making it all visible
		log.setVisible(true);
		
	}
	public static void main(String[] args) {
		Loggin();
	}
}

class logging implements ActionListener {
	JFrame log;
	JTextField pass;
	JButton but[];
	logging(JFrame log, JTextField p, JButton but[]) {
		this.log = log;
		this.pass = p;
		this.but = but;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(but[0])) {
			if (pass.getText().compareTo("op123") == 0) {
				GUITesting(0);
				log.dispatchEvent(new WindowEvent(log, WindowEvent.WINDOW_CLOSING));
			}
			else if (pass.getText().compareTo("user123") == 0) {
				GUITesting(1);
				log.dispatchEvent(new WindowEvent(log, WindowEvent.WINDOW_CLOSING));
			}
		}
		else if (e.getSource().equals(but[1]))
			System.exit(0);

	}
	
	
	void GUITesting(int accessLevel) {
		RadarSimulator rs = new RadarSimulator();
		VMS v = new VMS(rs); 
		final String VIEW[] = { "Table view", "Map view"};
		JFrame f = new JFrame("TESTING VMS");		
		f.setSize(700,600);
		
		FilterPanel fp = new FilterPanel();
		TablePanel tp = new TablePanel(v.filterData(fp.getFilter()), rs, v, accessLevel);
		MapPanel mp = new MapPanel(v, rs, fp);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.add(tp, VIEW[0]);
		tabbedPane.add(mp,VIEW[1]);
		f.setJMenuBar(new MenuBar());
		if (accessLevel == 0)
			f.add(fp, BorderLayout.WEST);
		f.add(tabbedPane, BorderLayout.CENTER);
				
		Timer timer = new Timer("Update");		
		UpdateTask ut = new UpdateTask(v, tp, fp, f, rs);
		timer.schedule(ut, (int)(rs.getStartTime()*1000), (int)(rs.getTimeStep()*1000)); // (FUNCTION, DELAY, PERIOD)
		
		f.setVisible(true);
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
