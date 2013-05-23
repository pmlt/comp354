import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridLayout;

public class FilterPanel extends JPanel implements ActionListener {
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton[] buttons = new JRadioButton[6];
	private int filter = 0;
	final private String[] buttonNames = {"All",
			"1 - Human (swimmer)",
			"2 - Speed Boat",
			"3 - Fishing Boat",
			"4 - Cargo Vessel",
			"5 - Passenger Vessel"};
	
	public FilterPanel() {
		setLayout(new GridLayout(6,1));
		for (int i=0; i<buttonNames.length; i++) {
			buttons[i] = new JRadioButton(buttonNames[i]);
//			buttons[i].setActionCommand(buttonNames[i]);
			buttons[i].addActionListener(this);
			group.add(buttons[i]);
			this.add(buttons[i]);
		}
		buttons[0].setSelected(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for (int i=0; i<buttonNames.length; i++) {
			if (action.equals(buttonNames[i])) {
				filter = i;
				System.out.println("Filter set to " + i);
				break;
			}
		}
	}
	
	public int getFilter()	{return filter;	}
}
