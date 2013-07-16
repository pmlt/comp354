import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

class LogIn implements ActionListener {
	JFrame log;
	JTextField pass;
	JButton but[] = {new JButton("Login"), new JButton("Exit")};
	LogIn() {
		log = new JFrame("Loggin");
		log.setSize(200, 150);
		log.setResizable(false);
		log.setJMenuBar(new MenuBar());

		//Creating fields
		JLabel label = new JLabel("Enter you password");
//		JLabel op = new JLabel("Operator: op123");
//		JLabel us = new JLabel("User: user123");
		pass = new JTextField();
		
		JPanel output = new JPanel();
		output.setLayout(new GridLayout(3,1));		
		JPanel commands = new JPanel();
		
		// Creating functions
		but[0].addActionListener(this);
		but[1].addActionListener(this);
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
