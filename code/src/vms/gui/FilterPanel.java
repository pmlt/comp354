package vms.gui;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import common.Vessel.VesselType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class FilterPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3983258595803965453L;
	private Map<VesselType, Boolean> filter;
	final static private Map<VesselType, String> VESSEL_NAMES;
	static {
		VESSEL_NAMES = new HashMap<VesselType, String>();
		VESSEL_NAMES.put(VesselType.SWIMMER, "1 - Human (swimmer) ");
		VESSEL_NAMES.put(VesselType.SPEED_BOAT, "2 - Speed Boat");
		VESSEL_NAMES.put(VesselType.FISHING_BOAT, "3 - Fishing Boat");
		VESSEL_NAMES.put(VesselType.CARGO_BOAT, "4 - Cargo Vessel");
		VESSEL_NAMES.put(VesselType.PASSENGER_VESSEL, "5 - Passenger Vessel");
		VESSEL_NAMES.put(VesselType.UNKNOWN, "6 - Unknown");
	}
	
	public FilterPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Filter by:")));
		filter = new HashMap<VesselType, Boolean>();
		for (VesselType vtype : VesselType.values()) {
			JCheckBox chk = new JCheckBox(VESSEL_NAMES.get(vtype));
			chk.addActionListener(this);
			chk.setSelected(true);
			this.add(chk);
			filter.put(vtype, true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for (VesselType vtype : VesselType.values()) {
			if (action.equals(VESSEL_NAMES.get(vtype))) {
				filter.put(vtype, !filter.get(vtype));
				break;
			}
		}
	}
	
	public Map<VesselType, Boolean> getActiveFilters() {
		return filter;
	}
}
