package vms.gui;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MenuBar extends JMenuBar implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8761848412891724604L;
	private JFrame _ParentWindow;
	
	public MenuBar(JFrame window) {
		_ParentWindow = window;
		add(createFileMenu());
	}

	public JMenu createFileMenu() {
		//Build the first menu.
		JMenu menu = new JMenu("File");
		
		JMenuItem menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menuItem.getAccessibleContext().setAccessibleDescription("Exits the program");
		
		menu.add(menuItem);

		return menu;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		WindowEvent windowClosing = new WindowEvent(_ParentWindow, WindowEvent.WINDOW_CLOSING);
		_ParentWindow.dispatchEvent(windowClosing);
	}
}
