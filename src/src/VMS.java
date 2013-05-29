// Manipulation of the vessel information
// is made here
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class VMS {
	private final int maxBoat = 100;
	private double[][] distance;
	private String[] risk;
	private int orderColumn;
	private boolean orderType;
	private Object[][] temp;
	VMS() {
		distance = new double[maxBoat][maxBoat];
		risk = new String[maxBoat];
		orderColumn = 0;
		orderType = true;
	}
	void update(RadarSimulator rs) {
		updateDistance(rs);
	}
		
	private void updateDistance(RadarSimulator rs)  {
		for (int i=0; i<rs.getCount(); i++)
			for (int j=i+1; j<rs.getCount(); j++) {
//				if (i == j)		distance[i][j] = 0;
//				else {
					double deltaX = rs.getXPosBoat(i) - rs.getXPosBoat(j);
					double deltaY = rs.getYPosBoat(i) - rs.getYPosBoat(j);
					double deltaD = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
					
					distance[i][j] = deltaD;
					distance[j][i] = deltaD;
					
/*					if (deltaD < 50.0) {
						highRisk[i] = true;
						highRisk[j] = true;
					}
					else if (deltaD < 200.0) {
						lowRisk[i] = true;
						lowRisk[j] = true;
						
					}
					else {
						lowRisk[i] = false;
						lowRisk[j] = false;
						highRisk[i] = false;
						highRisk[j] = false;
					}
				}// END OF ELSE
*/
					if (deltaD <= 50) {
						risk[i] = "high";
						risk[j] = "high";
					}
					else if (deltaD <= 200) {
						if (risk[i] != "high")
							risk[i] = "low";
						if (risk[j] != "high")
							risk[j] = "low";
					}
					else {
						if (risk[i] != "high" && risk[i] != "low")
							risk[i] = "none";
						if (risk[j] != "high" && risk[i] != "low")
							risk[j] = "low";
					}
			
			}// END OF FOR LOOP
		
		rs.addRisk(risk);
	}
	void removeRow(int index, int count) {
		for (int i = index; i<count; i++) {
			risk[i] = risk[i+1];
		}
			
	}
	final Object[][] filterData(RadarSimulator rs, boolean[] filter) {
//		if (type == 0)
//			return rs.getData();
		
		Object[][] filteredData;
		int size = 0;
		for (int i=0; i<rs.getCount(); i++)
			if (filter[(Integer)rs.getRow(i)[1]-1])
				size++;
//		System.out.println("Size of new array is: " + size);

		filteredData = new Object[size][9];
		int row = 0;
		for (int i=0; i<rs.getCount() && row<size; i++)
			if (filter[(Integer)rs.getRow(i)[1]-1]) {
				filteredData[row] = rs.getRow(i);
				row++;
			}
		// The following decides in what order the data goes though
		// Using QuickSort
		if (rs.getCount() > 1) {
			temp = filteredData;
			quicksort(temp,0, size-1);
			filteredData = temp;
			temp = null;
		}
/*		for (int i=0; i<filteredData.length; i++) {
			for (int j=0; j<8; j++) {
//				System.out.print(filteredData[i][j] + "\t");
			}
			System.out.println();
		}*/
		return filteredData;
	}
	
	void setOrderBy(int index) {
		orderColumn = index;
	}
	void setOrderType(int x) {
		if (x == 0)					orderType = true;
		else if (x == 1)			orderType = false;
	}
	private void quicksort(Object[][] obj, int left, int right) {
		if (left < right) {
			int pivotindex = right /2;
			int pivotNewIndex = partition(obj, left, right, pivotindex);
			quicksort(obj,left,pivotNewIndex-1);
			quicksort(obj, pivotNewIndex+1, right);
		}
	}
	private int partition( Object[][] obj, int left, int right, int pivot) {
		Object[] value = obj[pivot];
		Object[] temp = obj[right];
		obj[right] = value;
		obj[pivot] = temp;
		int store = left;
		for (int i = left; i < right; i++) {
			if (compare(Double.parseDouble(obj[i][orderColumn].toString()), Double.parseDouble(value[orderColumn].toString()))) {
				Object[] temp1 = obj[i];
				temp = obj[store];
				obj[i] = temp;
				obj[store++] = temp1;
			}
		}
		temp = obj[right];
		obj[right] = obj[store];
		obj[store] = temp;
		return store;
	}
	
	private boolean compare(double x, double y) {
		if (orderType) return (x <= y);
		else return (x >= y);
	}
} // END IF ,java


