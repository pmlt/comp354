package vms.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderPanel extends JPanel implements ChangeListener {
	private final int MIN = 1;
	private final int MAX = 6;
	private final int INIT = 1;
	private MapPanel mp;
	
	public SliderPanel(MapPanel mp) {
		this.mp = mp;
		this.setMaximumSize(new Dimension(100, 220));
		createSlider();
	}
	
	private void createSlider() {
		JSlider scale = new JSlider(JSlider.VERTICAL,
                MIN, MAX, INIT);
		scale.addChangeListener(this);
		//Turn on labels at major tick marks.
		scale.setMajorTickSpacing(1);
//		scale.setMinorTickSpacing(100);
		scale.setPaintTicks(true);
		scale.setPaintLabels(true);
		this.add(scale);
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int range = (int)source.getValue();
	        mp.changeRange(range);
	    }
	}
}
