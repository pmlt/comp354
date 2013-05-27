import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePanel extends JPanel implements ActionListener {
	private JTable table;
	private JScrollPane scrollPane;
	private JButton adding;
	private JButton remover;
	private RadarSimulator rs;
    final private String[] columnNames = {"Vessel ID",
            "Type",
            "X Position",
            "Y Position",
            "Speed",
            "Course",
            "Distance",
            "Update Time"};
    final private String[] buttonNames = { "Add New..", "Remove Selected"};

    public TablePanel(final Object[][] obj, RadarSimulator rs) {
    	this.rs = rs;
//        super(new GridLayout(1,0));
        table = new JTable(obj, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setAutoCreateRowSorter(true);
//		table.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		//Create Button to add
		adding = new JButton(buttonNames[0]);
		adding.addActionListener(this);
		remover = new JButton(buttonNames[1]);
		remover.addActionListener(this);
        add(scrollPane, BorderLayout.CENTER);
        add(adding, BorderLayout.SOUTH);
        add(remover, BorderLayout.SOUTH);
    }
    
    void update(final Object[][] obj) {
		scrollPane.remove(table);
        table = new JTable(obj, columnNames);
        scrollPane.add(table);
        scrollPane.setViewportView(table);
    }
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(remover)) {
			int row = table.getSelectedRow();
			System.out.println( table.getValueAt(row, 0));
			rs.removeFromID(table.getValueAt(row, 0));
		}			
		else if (arg0.getSource().equals(adding))
			System.out.println("ADD");
	}

}