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
	
	MenuBar() {
		add(createFileMenu());
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
}

class AppExit implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}