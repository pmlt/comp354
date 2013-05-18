import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;

public class TablePanel extends JPanel {
	 
    public TablePanel(VMS v) {
        super(new GridLayout(1,0));
 
        String[] columnNames = {"Vessel ID",
                                "Type",
                                "X Position",
                                "Y Position",
                                "Speed",
                                "Course",
                                "Distance",
                                "Update Time"};

 
        final JTable table = new JTable(v.getData(), columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
  
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
  
}
