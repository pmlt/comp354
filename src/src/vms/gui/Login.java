package vms.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login implements ActionListener {
	MainGUI _Main;
	JFrame log;
	JTextField pass;
	JButton but[] = {new JButton("Login"), new JButton("Exit")};
	public Login (MainGUI main) {
		_Main = main;
		log = new JFrame("Loggin");
		log.setSize(200, 150);
		log.setResizable(false);
		log.setJMenuBar(new MenuBar(log));

		//Creating fields
		JLabel label = new JLabel("Enter you password");
//		JLabel op = new JLabel("Operator: op123");
//		JLabel us = new JLabel("User: user123");
		pass = new JTextField();
		
		JPanel output = new JPanel();
		output.setLayout(new GridLayout(3,1));		
		JPanel commands = new JPanel();
		
		// Creating functions
		but[0].addActionListener(this);
		but[1].addActionListener(this);
		// Adding fields to panels
//		output.add(op);
//		output.add(us);
		output.add(label);
		output.add(pass);
		
		commands.add(but[0]);
		commands.add(but[1]);
		// Adding Panels to Frame
		log.add(output, BorderLayout.CENTER);
		log.add(commands, BorderLayout.SOUTH);
		log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show() {
		log.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(but[0])) {
			if (pass.getText().compareTo("op123") == 0) {
				_Main.showRadar(MainGUI.UserIdentity.PRIVILEGED);
				log.dispatchEvent(new WindowEvent(log, WindowEvent.WINDOW_CLOSING));
			}
			else if (pass.getText().compareTo("user123") == 0) {
				_Main.showRadar(MainGUI.UserIdentity.NORMAL);
				log.dispatchEvent(new WindowEvent(log, WindowEvent.WINDOW_CLOSING));
			}
		}
		else if (e.getSource().equals(but[1])) {
			//This is the proper way to close
			WindowEvent windowClosing = new WindowEvent(log, WindowEvent.WINDOW_CLOSING);
			log.dispatchEvent(windowClosing);
		}

	}

}
