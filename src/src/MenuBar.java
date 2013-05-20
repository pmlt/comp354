import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
	
	MenuBar() {
		add(createFileMenu());
		add(createViewMenu());
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
	
	JMenu createViewMenu() {
		JMenu menu = new JMenu("View");
		
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Table");
		rbMenuItem.setSelected(true);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Map");
		group.add(rbMenuItem);

		menu.add(rbMenuItem);		
		return menu;
	}
	
}

class AppExit implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}