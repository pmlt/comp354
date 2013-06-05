import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.JPanel;
import java.awt.Graphics;

public class MapPanel extends JPanel {
	private VMS vms;
	private RadarSimulator rs;
	private FilterPanel fp;
	public MapPanel(final VMS vms, final RadarSimulator rs, final FilterPanel fp) {
		this.vms = vms;
		this.rs = rs;
		this.fp = fp;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		super.paintComponent(g);
		int wWidth = getWidth();
		int wHeight = getHeight();
		g.drawLine(wWidth/2, 0, wWidth/2, wHeight);
		g.drawLine(0, wHeight/2, wWidth, wHeight/2);
		final Object[][] obj = vms.filterData(fp.getFilter());
		for (int i=0; i<obj.length;i++) {
			int j = (int) Math.ceil((Double)obj[i][2]) * wWidth/ 2  / rs.getRange() + wWidth/2;
			int k = (int) Math.ceil((Double)obj[i][3]) * wHeight/-2 / rs.getRange() + wHeight/2;
			g.fillOval(j-3, k-3, 6, 6);
			g.drawLine(j, k, wWidth/2, wHeight/2);
			
			// Draws circles around the ships; adds color if there is a high-risk or low-risk alert
			if (obj[i][8].toString().equals("high")) 
				g2.setColor(Color.red);
			g.drawOval(j-25, k-25, 50, 50);
			g2.setColor(Color.black);
			
			if (obj[i][8].toString().equals("low")) 
				g2.setColor(Color.yellow);
			g.drawOval(j-100, k-100, 200, 200);
			g2.setColor(Color.black);
		}
	}

}
