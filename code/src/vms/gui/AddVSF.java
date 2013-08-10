package vms.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRootPane;
import javax.swing.JFileChooser;

public class AddVSF extends JFrame implements ActionListener, WindowListener {
	MainGUI _AddFile;
	JFrame addFile;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JTextField inputFile;
	JTextField host;
	JTextField port;
	JButton but[] = {new JButton("Browse"), new JButton("Add"), new JButton("Cancel")};

	public AddVSF (MainGUI main) {
		_AddFile = main;
		addFile = new JFrame("Add New Simulator File");
		addFile.addWindowListener(this);
		addFile.setSize(350, 150);
		addFile.setResizable(false);
		addFile.setLocationRelativeTo(null);


		//Creating fields
		label1 = new JLabel("File Name:");
		label2 = new JLabel("Host IP:");
		label3 = new JLabel("Port:");
		//		JLabel ad = new JLabel("Operator: op123");
		inputFile = new JTextField();
		host = new JTextField();
		port = new JTextField();

		JPanel output = new JPanel();
		output.setLayout(new GridLayout(3,3));		
		JPanel commands = new JPanel();

		// Creating functions
		but[0].addActionListener(this);
		but[1].addActionListener(this);
		but[2].addActionListener(this);
		// Adding fields to panels
		//		output.add(op);
		//		output.add(us);
		output.add(label1);
		output.add(inputFile);
		output.add(but[0]);
		output.add(label2);
		output.add(host);
		output.add(new JLabel(""));
		output.add(label3);
		output.add(port);
		output.add(new JLabel(""));

		commands.add(but[1]);
		commands.add(but[2]);
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
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(AddVSF.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				inputFile.setText(c.getCurrentDirectory().toString() + c.getSelectedFile().getName());
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				inputFile.setText("");
			}		}
		else if (e.getSource().equals(but[1])) {
			if (inputFile.getText().compareTo("") != 0 && host.getText().compareTo("") != 0 && port.getText().compareTo("") != 0) {
				String[] arguments = new String[]{host.getText(), port.getText(), inputFile.getText()};
				try {
					simulator.Mainfunction.main(arguments);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				addFile.setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "Please enter all required information!", "File Error", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if (e.getSource().equals(but[2])) {
			addFile.setVisible(false);
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

}
