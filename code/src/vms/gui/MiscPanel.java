package vms.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import common.Coord;

public class MiscPanel extends JPanel implements ActionListener {
	private JTextField xCoord;
	private JTextField yCoord;
	private JButton center;
	private JTextField zoomFactor;
	private JButton zoom;
	private MapPanel mp;
	
	public MiscPanel(MapPanel map) {
		mp = map;
		xCoord = new JTextField();
//		xCoord.setMaximumSize(new Dimension(100,20));
		yCoord = new JTextField();
//		yCoord.setMaximumSize(new Dimension(100,20));
		center = new JButton("Center");
		center.addActionListener(this);
		
		zoomFactor = new JTextField();
//		zoomFactor.setMaximumSize(new Dimension(100,20));
		zoom = new JButton("Zoom");
		zoom.addActionListener(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new JLabel("Input desired coordinates"));
		this.add(xCoord);
		this.add(yCoord);
		this.add(center);
		this.add(new JLabel("Zoom factor (%)"));
		this.add(zoomFactor);
		this.add(zoom);
//		this.setMinimumSize(new Dimension(200,400));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(center))
			mp.changeCenter(new Coord(Integer.parseInt(xCoord.getText()),Integer.parseInt(yCoord.getText())));
		else if (e.getSource().equals(zoom))
			mp.changeRange((double)Integer.parseInt(zoomFactor.getText())/100);
	}

}
