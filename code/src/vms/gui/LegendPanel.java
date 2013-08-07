package vms.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import common.Vessel.VesselType;

public class LegendPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9004280718191289072L;
	final private VesselType[] types = {VesselType.SWIMMER, VesselType.SPEED_BOAT, VesselType.FISHING_BOAT,
			VesselType.CARGO_BOAT, VesselType.PASSENGER_VESSEL, VesselType.UNKNOWN};
	
	public LegendPanel() {
		// setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.BLACK);
		setMaximumSize(new Dimension(600, 30));
		setLayout(new GridLayout(3, 3));
		Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
		JLabel label = new JLabel("LEGEND");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		this.add(label);
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		for (int i=0; i<types.length; i++) {
			label = new JLabel("    " + getType(types[i]));
			label.setFont(font);
			label.setForeground(getColor(types[i]));
			label.setBackground(Color.BLACK);
			this.add(label);
		}	
	}
	
	public String getType(VesselType type) {
		switch(type) {
		case SWIMMER:
			return "1 - Human (swimmer)";
		case SPEED_BOAT:
			return "2 - Speed Boat";
		case FISHING_BOAT:
			return "3 - Fishing Boat";
		case CARGO_BOAT:
			return "4 - Cargo Vessel";
		case PASSENGER_VESSEL:
			return "5 - Passenger Vessel";
		case UNKNOWN:
			return "6 - Unknown";
		default:
			return "6 - Unknown";
		}
	}
	
	public Color getColor(VesselType type) {
		switch(type) {
		case SWIMMER:
			return Color.PINK;
		case SPEED_BOAT:
			return Color.GREEN;
		case FISHING_BOAT:
			return Color.CYAN;
		case CARGO_BOAT:
			return Color.ORANGE;
		case PASSENGER_VESSEL:
			return Color.MAGENTA;
		case UNKNOWN:
			return Color.WHITE;
		default:
			return Color.WHITE;
		}
	}
}
