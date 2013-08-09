package vms.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRootPane;

public class AddVSF implements ActionListener, WindowListener {
	MainGUI _AddFile;
	JFrame addFile;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JTextField inputFile;
	JTextField host;
	JTextField port;
	JButton but[] = {new JButton("Login"), new JButton("Exit")};
	public AddVSF (MainGUI main) {
		_AddFile = main;
		addFile = new JFrame("Add New Simulator File");
		addFile.addWindowListener(this);
		addFile.setSize(250, 450);
		addFile.setResizable(false);
		addFile.setLocationRelativeTo(null);
		addFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//Creating fields
		label1 = new JLabel("File Name:");
		label2 = new JLabel("Host IP:");
		label3 = new JLabel("Port:");
//		JLabel ad = new JLabel("Operator: op123");
		inputFile = new JTextField();
		host = new JTextField();
		port = new JTextField();
		
		JPanel output = new JPanel();
		output.setLayout(new GridLayout(3,1));		
		JPanel commands = new JPanel();
		
		// Creating functions
		but[0].addActionListener(this);
		but[1].addActionListener(this);
		// Adding fields to panels
//		output.add(op);
//		output.add(us);
		output.add(label1);
		output.add(inputFile);
		output.add(label2);
		output.add(host);
		output.add(label3);
		output.add(port);

		commands.add(but[0]);
		commands.add(but[1]);
		// Adding Panels to Frame
		addFile.add(output, BorderLayout.CENTER);
		addFile.add(commands, BorderLayout.SOUTH);
		
		JRootPane rootPane = addFile.getRootPane();
	    rootPane.setDefaultButton(but[0]);
	}
	
	public void show() {
		inputFile.setText("");
		host.setText("");
		port.setText("");
		addFile.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(but[0])) {
			if (comboBox.getSelectedItem().toString() == "operator" && pass.getText().compareTo("op123") == 0) {
//				_Main.showRadar(MainGUI.UserIdentity.OPERATOR);
//				addFile.setVisible(false);
			}
		}
		else if (e.getSource().equals(but[1])) {
			close();
		}
		else if (e.getSource().equals(comboBox)) {
			if (comboBox.getSelectedItem().toString() == "operator") {
				label2.setEnabled(true);
				pass.setEnabled(true);
			}
			else if (comboBox.getSelectedItem().toString() == "user") {
				label2.setEnabled(false);
				pass.setEnabled(false);
				pass.setText("");
			}

		}

	}
	
	public void close() {
		//This is the proper way to close
		WindowEvent windowClosing = new WindowEvent(addFile, WindowEvent.WINDOW_CLOSING);
		addFile.dispatchEvent(windowClosing);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
