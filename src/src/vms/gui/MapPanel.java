package vms.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import vms.*;
import vms.Alert.AlertType;
import common.*;

import java.awt.Graphics;
import java.util.Calendar;
import java.util.List;

public class MapPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3982174526003182203L;
	private final int RANGE = 5000;
	private final int HIGH_RISK = 50;
	private final int LOW_RISK = 200;
	
	private List<Vessel> _Vessels;
	private List<Alert> _Alerts;
	
	public void update(final List<Alert> alerts, final List<Vessel> vessels) {
		_Alerts = alerts;
		_Vessels = vessels;
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		super.paintComponent(g);
		int wWidth = getWidth();
		int wHeight = getHeight();
		g.drawLine(wWidth/2, 0, wWidth/2, wHeight);
		g.drawLine(0, wHeight/2, wWidth, wHeight/2);
		Calendar now = Calendar.getInstance();
		for (Vessel v : _Vessels) {
			Coord coords = v.getCoord(now);
			int j = (int) Math.ceil((double)coords.x()) * wWidth/ 2  / RANGE + wWidth/2;
			int k = (int) Math.ceil((double)coords.y()) * wHeight/-2 / RANGE + wHeight/2;
			g.fillOval(j-1, k-1, 2, 2);
			g.drawLine(j, k, wWidth/2, wHeight/2);
			
			//Search for worst alert
			Alert worstAlert = null;
			for (Alert a : _Alerts) {
				if (a.contains(v)) {
					if (worstAlert == null || a.getType() == AlertType.HIGHRISK) {
						worstAlert = a;
					}
				}
			}
			
			// Draws circles around the ships; adds color if there is a high-risk or low-risk alert
			if (worstAlert != null && worstAlert.getType() == AlertType.HIGHRISK)
				g2.setColor(Color.red);
			
			g.drawOval(j - wWidth*HIGH_RISK/(2*RANGE), k-wHeight*HIGH_RISK/(2*RANGE),
					wWidth*HIGH_RISK/RANGE, wHeight*HIGH_RISK/RANGE);
			g2.setColor(Color.black);
			
			if (worstAlert != null && worstAlert.getType() == AlertType.LOWRISK)
				g2.setColor(Color.yellow);
			
			g.drawOval(j - wWidth*LOW_RISK/(2*RANGE), k-wHeight*LOW_RISK/(2*RANGE),
					wWidth*LOW_RISK/RANGE, wHeight*LOW_RISK/RANGE);
			g2.setColor(Color.black);
		}
	}

}
