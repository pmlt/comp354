import java.awt.Paint;
import javax.swing.JPanel;
import java.awt.Graphics;

public class MapPanel extends JPanel {
	private RadarSimulator rs;
	public MapPanel(final RadarSimulator rs) {
		this.rs = rs;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int wWidth = getWidth();
		int wHeight = getHeight();
		g.drawLine(wWidth/2, 0, wWidth/2, wHeight);
		g.drawLine(0, wHeight/2, wWidth, wHeight/2);
		final Object[][] obj = rs.getData();
		for (int i=0; i <rs.getCount();i++) {
			int j = (int) Math.ceil((Double)obj[i][2]) * wWidth/ 2  / rs.getRange() + wWidth/2;
			int k = (int) Math.ceil((Double)obj[i][3]) * wHeight/-2 / rs.getRange() + wHeight/2;
			g.drawOval(j, k, 1, 1);
			g.drawLine(j, k, wWidth/2, wHeight/2);
		}
	}

}
