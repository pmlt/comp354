import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {
	private JTable table;
	private JScrollPane scrollPane;
    final private String[] columnNames = {"Vessel ID",
            "Type",
            "X Position",
            "Y Position",
            "Speed",
            "Course",
            "Distance",
            "Update Time"};

    public TablePanel(final Object[][] obj) {
        super(new GridLayout(1,0));
        table = new JTable(obj, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setAutoCreateRowSorter(true);
//		table.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    void update(final Object[][] obj) {
		scrollPane.remove(table);
        table = new JTable(obj, columnNames);
        scrollPane.add(table);
        scrollPane.setViewportView(table);
    }
  
}
