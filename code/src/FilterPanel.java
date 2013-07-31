import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterPanel extends JPanel implements ActionListener {
	private boolean[] filter;
	private boolean screenUpdate;
	final private int VESSEL_TYPES = 5;
	final private String[] VESSEL_NAMES = {
			"1 - Human (swimmer)",
			"2 - Speed Boat",
			"3 - Fishing Boat",
			"4 - Cargo Vessel",
			"5 - Passenger Vessel"};
	
	public FilterPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		screenUpdate = true;
		filter = new boolean[VESSEL_TYPES];
		JCheckBox[] checkboxes = new JCheckBox[VESSEL_TYPES];
		JLabel label = new JLabel("Filter by");
		this.add(label);
		for (int i=0; i<VESSEL_TYPES; i++) {
			checkboxes[i] = new JCheckBox(VESSEL_NAMES[i]);
			checkboxes[i].addActionListener(this);
			checkboxes[i].setSelected(true);
			this.add(checkboxes[i]);
			filter[i] = true;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for (int i=0; i<VESSEL_TYPES; i++) {
			if (action.equals(VESSEL_NAMES[i])) {
				filter[i] = !filter[i];
				screenUpdate = true;
				break;
			}
		}
	}
	
	public boolean[] getFilter()	{return filter;	}
	public boolean getScreenStatusUpdate()	{	return screenUpdate; }
	public void update() {	screenUpdate = false;	}
}
