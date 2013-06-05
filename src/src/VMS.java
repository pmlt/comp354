// Manipulation of the vessel information
// is made here

import java.sql.Time;

public class VMS {
	private final int MAX_BOATS = 100;
	private RadarSimulator radar;
	private int orderColumn;
	private boolean orderType;
	private Object[][] data;
	private Object[][] temp;
	
	VMS(RadarSimulator radar) {
		this.radar = radar;
		data = new Object[MAX_BOATS][9];
		updateData();
		orderColumn = 0;
		orderType = true;
	}
	
	void update() {
		updateData();
	}
	
	private void updateData() {
		int count = 0;
		for (Vessel v : radar.getVessels()) {
			data[count][0] = v.getVesselId();
			data[count][1] = v.getType();
			data[count][2] = v.getXPosition();
			data[count][3] = v.getYPosition();
			data[count][4] = radar.calculateSpeed(v);
			data[count][5] = radar.calculateCourse(v);
			data[count][6] = radar.calculateDistance(v);
			data[count][7] = new Time(System.currentTimeMillis());
			data[count][8] = calculateRisk(v);
			count++;
		}
		System.out.println();
	}
	
	String calculateRisk(Vessel ve) {
		String risk = "none";
		for (Vessel v2 : radar.getVessels()) {
			if (ve.getVesselId() == v2.getVesselId())
				continue;
			double deltaX = ve.getXPosition() - v2.getXPosition();
			double deltaY = ve.getYPosition() - v2.getYPosition();
			double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
			
			if (distance < 50)
				risk = "high";
			else if (distance < 200 && risk != "high")
				risk = "low";
		}
		return risk;
	}
	
	final Object[][] filterData(boolean[] filter) {
		Object[][] filteredData;
		int size = 0;
		for (int i=0; i<radar.getCount(); i++)
			if (filter[(Integer)data[i][1]-1])
				size++;

		filteredData = new Object[size][9];
		int row = 0;
		for (int i=0; i<radar.getCount() && row<size; i++)
			if (filter[(Integer)data[i][1]-1]) {
				filteredData[row] = data[i];
				row++;
			}
		// The following decides in what order the data goes through
		// Using QuickSort
		if (radar.getCount() > 1) {
			temp = filteredData;
			quicksort(temp, 0, size-1);
			filteredData = temp;
			temp = null;
		}
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
			int pivotindex = right/2;
			int pivotNewIndex = partition(obj, left, right, pivotindex);
			quicksort(obj,left,pivotNewIndex-1);
			quicksort(obj, pivotNewIndex+1, right);
		}
	}
	private int partition(Object[][] obj, int left, int right, int pivot) {
		Object[] value = obj[pivot];
		Object[] temp = obj[right];
		obj[right] = value;
		obj[pivot] = temp;
		int store = left;
		for (int i=left; i<right; i++) {
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
		if (orderType) return (x < y);
		else return (x > y);
	}
} // END IF ,java


