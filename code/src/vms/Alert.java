package vms;

import java.util.*;

import common.Vessel;

public class Alert {
	private AlertType _Type;
	private List<Vessel> _Vessels;
	
	public enum AlertType {
		LOWRISK,
		HIGHRISK,
		NONE
	}
	public Alert(AlertType type, List<Vessel> vessels) {
		_Type = type;
		_Vessels = vessels;
	}
	public AlertType getType() {
		return _Type;
	}
	public List<Vessel> getVessels() {
		return _Vessels;
	}
	public boolean contains(Vessel v) {
		return _Vessels.contains(v);
	}
}
