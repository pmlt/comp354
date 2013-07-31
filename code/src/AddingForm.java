import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class AddingForm extends JFrame implements ActionListener{
	private JTextField[] fields = {	new JTextField(""),
									new JTextField(""),
									new JTextField(""),
									new JTextField(""),
									new JTextField(""),
									new JTextField("")};
	private JLabel[] label = {	new JLabel("Vessel id:\t"),
								new JLabel("Type:\t\t"),
								new JLabel("X Position:\t"),
								new JLabel("Y Position:\t"),
								new JLabel("X Velocity:\t"),
								new JLabel("Y Velocity:\t")};
	private JButton[] button = {	new JButton("Add"),
									new JButton("Cancel")};
	
	private RadarSimulator rs;
	
	AddingForm(RadarSimulator rs) {
		this.rs = rs;
		setName("Adding Vessel");
		setSize(200,200);
		setResizable(false);
		add(Form(),BorderLayout.CENTER);
		add(Button(), BorderLayout.SOUTH);
	}
	JPanel Form() {
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(6,2));
		for (int i=0; i< 6;i++){
			form.add(label[i]);
			form.add(fields[i]);
		}
		return form;
	}
	JPanel Button(){
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(1,2));
		button[0].addActionListener(this);
		button[1].addActionListener(this);
		form.add(button[0]);
		form.add(button[1]);
		
		return form;
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button[0])) {
			Vessel newVessel = new Vessel(
										fields[0].getText(),
										Integer.parseInt(fields[1].getText()), 
										Double.parseDouble(fields[2].getText()), 
										Double.parseDouble(fields[3].getText()),
										Double.parseDouble(fields[4].getText()),
										Double.parseDouble(fields[5].getText()),
										0);
			rs.addVessel(newVessel);
		}
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
