package vms.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import common.Coord;

public class DataPanel extends JPanel implements MapPanel.Observer {
	private JPanel centerPanel;
	private JTextField centerData;
	private JPanel pointerPanel;
	private JTextField pointerData;
	private JPanel zoomPanel;
	private JTextField zoomData;
	
	public DataPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(1000, 100));
		
		centerPanel = new JPanel();
		centerPanel.add(new JLabel("Map center:"));
		centerData = new JTextField();
		centerData.setText("(0, 0)");
		centerData.setEditable(false);
		centerData.setPreferredSize(new Dimension(120,20));
		centerPanel.add(centerData);
		this.add(centerPanel);
		
		pointerPanel = new JPanel();
		pointerPanel.add(new JLabel("Current location:"));
		pointerData = new JTextField();
		pointerData.setEditable(false);
		pointerData.setPreferredSize(new Dimension(120,20));
		pointerPanel.add(pointerData);
		this.add(pointerPanel);
		
		zoomPanel = new JPanel();
		zoomPanel.add(new JLabel("Zooming:"));
		zoomData = new JTextField();
		zoomData.setText("100%");
		zoomData.setEditable(false);
		zoomData.setPreferredSize(new Dimension(60,20));
		zoomPanel.add(zoomData);
		this.add(zoomPanel);
//		this.setMinimumSize(new Dimension(200,400));
	}

	@Override
	public void update(Coord center, Coord pointer, int range, int maxRange, double width, double height) {
		centerData.setText("(" + (int)center.x() + ", " + (int)center.y() + ")");
		pointerData.setText("(" + (int)pointer.x() + ", " + (int)pointer.y() + ")");
		zoomData.setText(100*maxRange/range + "%");
		this.repaint();
	}

}
