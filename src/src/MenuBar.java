import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MenuBar extends JMenuBar {
	
	MenuBar(JPanel c, String[] v) {
		add(createFileMenu());
		add(createViewMenu(c, v));
	}

	JMenu createFileMenu() {
		//Build the first menu.
		JMenu menu = new JMenu("File");
		
		JMenuItem menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new AppExit());
		menuItem.getAccessibleContext().setAccessibleDescription("Exits the program");
		
		menu.add(menuItem);

		return menu;
	}
	
	JMenu createViewMenu(JPanel c, String[] v) {
		JMenu menu = new JMenu("View");
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem1 = new JRadioButtonMenuItem(v[0]);
		JRadioButtonMenuItem rbMenuItem2 = new JRadioButtonMenuItem(v[1]);

		rbMenuItem1.setSelected(true);

		menu.add(rbMenuItem1);
		menu.add(rbMenuItem2);		
		group.add(rbMenuItem1);
		group.add(rbMenuItem2);

		
		ViewSwitch vs = new ViewSwitch(c, v);
		
		rbMenuItem1.addItemListener(vs);
		rbMenuItem2.addItemListener(vs);
			
		

		return menu;
	}
	
}
class ViewSwitch implements ItemListener {
	JPanel cards;
	String[] v;
	int i = 0;
	ViewSwitch(JPanel c, String[] v) {
		this.cards = c;
		this.v = v;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}
	
}

class AppExit implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}