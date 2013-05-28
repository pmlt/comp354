import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class FilterPanel extends JPanel implements ActionListener {
//	private ButtonGroup group = new ButtonGroup();
//	private JRadioButton[] buttons = new JRadioButton[VESSEL_TYPES+1];
	private boolean[] filter;
	private JCheckBox[] checkboxes;
	private boolean screenUpdate;
	final private int VESSEL_TYPES = 5;
	final private String[] VESSEL_NAMES = {
			"1 - Human (swimmer)",
			"2 - Speed Boat",
			"3 - Fishing Boat",
			"4 - Cargo Vessel",
			"5 - Passenger Vessel"};
	
	public FilterPanel() {
		setLayout(new GridLayout(6,1));
		screenUpdate = true;
		filter = new boolean[VESSEL_TYPES];
		checkboxes = new JCheckBox[VESSEL_TYPES];
		for (int i=0; i<VESSEL_TYPES; i++) {
			checkboxes[i] = new JCheckBox(VESSEL_NAMES[i]);
//			buttons[i].setActionCommand(buttonNames[i]);
			checkboxes[i].addActionListener(this);
//			group.add(buttons[i]);
			checkboxes[i].setSelected(true);
			this.add(checkboxes[i]);
			filter[i] = true;
		}
//		buttons[0].setSelected(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for (int i=0; i<VESSEL_TYPES; i++) {
			if (action.equals(VESSEL_NAMES[i])) {
				filter[i] = !filter[i];
				screenUpdate = true;
//				System.out.println("Filter set to " + i);
				break;
			}
		}
	}
	
	public boolean[] getFilter()	{return filter;	}
	public boolean getScreenStatusUpdate()	{	return screenUpdate; }
	public void update() {	screenUpdate = false;	}
}
