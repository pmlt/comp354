package vms.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import vms.*;
import vms.Alert.AlertType;
import common.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class AlertPanel extends JPanel {
	private List<Alert> _Alerts;
	private List<Vessel> _Vessels;

	public AlertPanel() {
		_Alerts = new ArrayList<Alert>();
		_Vessels = new ArrayList<Vessel>();
	}

	public void update(final List<Alert> alerts, final List<Vessel> vessels) {
		_Alerts = alerts;
		_Vessels = vessels;
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.green);
		super.paintComponent(g);
		int wWidth = getWidth();
		int wHeight = getHeight();
		for (Vessel v : _Vessels) {
			//Search for worst alert
			Alert worstAlert = null;
			for (Alert a : _Alerts) {
				if (a.contains(v)) {
					if (worstAlert == null || a.getType() == AlertType.HIGHRISK) {
						worstAlert = a;
					}
				}
			}
			
			// Draw circle green if none of the if statements are active
			
			// Draws circles around the ships; adds color if there is a high-risk or low-risk alert
			if (worstAlert != null && worstAlert.getType() == AlertType.HIGHRISK)
				g2.setColor(Color.red);
						
			if (worstAlert != null && worstAlert.getType() == AlertType.LOWRISK)
				g2.setColor(Color.yellow);
			
			g2.fillOval(wWidth/2, wHeight/2, wWidth, wHeight);
			g2.setColor(Color.green);
		}
	}

}
