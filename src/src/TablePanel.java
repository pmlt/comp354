import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class TablePanel extends JPanel implements ActionListener {
	private JComboBox nameList;
	private JComboBox orderList;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton adding;
	private JButton remover;
	private RadarSimulator rs;
	private VMS vms;
	private boolean updateScreen = false;
    final private String[] orderTypeNames = {"Ascending",
    		"Descending"    };
    final private String[] columnNames = {"Vessel ID",
            "Type",
            "X Position",
            "Y Position",
            "Speed",
            "Course",
            "Distance",
            "Update Time",
            "Risk"};
    final private String[] orderListNames = {"Vessel ID",
            "Type",
            "Speed",
            "Distance"};
    final private String[] buttonNames = { "Add New..", "Remove Selected"};
	JLabel[] label = {	new JLabel("Order by"),
						new JLabel("Type order")	};
    
	public TablePanel(final Object[][] obj, RadarSimulator rs, VMS vms) {
    	this.rs = rs;
    	this.vms = vms;
        table = new JTable(obj, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setOpaque(true);
		table.setAutoCreateRowSorter(true);
        
        //Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		
		JPanel commands = new JPanel();
		commands.setLayout(new GridLayout(2, 4));
		JPanel empty1 = new JPanel();
		JPanel empty2 = new JPanel();
		
		//Create Button to add
		adding = new JButton(buttonNames[0]);
		adding.addActionListener(this);
		remover = new JButton(buttonNames[1]);
		remover.addActionListener(this);
		nameList = new JComboBox(orderListNames);
		orderList = new JComboBox(orderTypeNames);
		nameList.setSelectedIndex(0);
		nameList.addActionListener(this);
		orderList.setSelectedIndex(0);
		orderList.addActionListener(this);
		commands.add(empty1);
		commands.add(empty2);
		commands.add(label[0]);
		commands.add(label[1]);
		commands.add(adding);
		commands.add(remover);
		commands.add(nameList);
		commands.add(orderList);
		
		add(scrollPane, BorderLayout.NORTH);
		add(commands, BorderLayout.SOUTH);
	}
	
	void update(final Object[][] obj) {
		scrollPane.remove(table);
		table = new JTable(obj, columnNames);
		scrollPane.add(table);
		for (int i = 0; i<table.getRowCount(); i++){
			if (table.getValueAt(i, 8) == "low") {
				
			}
			else if (table.getValueAt(i, 8) == "high") {
				
			}
		}
		scrollPane.setViewportView(table);
		updateScreen = false;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(remover)) {
			int row = table.getSelectedRow();
			System.out.println(table.getValueAt(row, 0));
			rs.removeVessel(table.getValueAt(row, 0).toString());
		}			
		else if (arg0.getSource().equals(adding)) {
			AddingForm af = new AddingForm(rs);
			af.setVisible(true);
		}
		else if (arg0.getSource().equals(nameList)) {
			JComboBox cb = (JComboBox)arg0.getSource();
			int index = 0;
			String column = (String)cb.getSelectedItem();
			if (column.compareToIgnoreCase(orderListNames[0]) == 0)
				index = 0;
			else if (column.compareToIgnoreCase(orderListNames[1]) == 0)
				index = 1;
			else if (column.compareToIgnoreCase(orderListNames[2]) == 0)
				index = 4;
			else if (column.compareToIgnoreCase(orderListNames[3]) == 0)
				index = 6;
			vms.setOrderBy(index);
		}
		else if (arg0.getSource().equals(orderList)) {
			JComboBox cb = (JComboBox)arg0.getSource();
			int index = 0;
			String column = (String)cb.getSelectedItem();
			if (column.compareToIgnoreCase(orderTypeNames[0]) == 0)
				index = 0;
			else if (column.compareToIgnoreCase(orderTypeNames[1]) == 0)
				index = 1;
			vms.setOrderType(index);
		}
		vms.update();
		updateScreen = true;
	}
	
	boolean getScreenStatusUpdate() {
		return updateScreen;
	}

}
