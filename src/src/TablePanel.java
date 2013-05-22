import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;

public class TablePanel extends JPanel {
	private JTable table;
    final private String[] columnNames = {"Vessel ID",
            "Type",
            "X Position",
            "Y Position",
            "Speed",
            "Course",
            "Distance",
            "Update Time"};

    public TablePanel(VMS v) {
        super(new GridLayout(1,0));
		table = new JTable(v.getData(), columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    void update(VMS v, int filter) {
//    	table = new JTable(v.getData(), columnNames);
        table = new JTable(v.filterData(filter), columnNames);
    }
  
}
