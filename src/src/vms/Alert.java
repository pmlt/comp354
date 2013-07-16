package vms;

import java.util.*;

import common.Vessel;

public class Alert {
	public enum AlertType {
		LOWRISK,
		HIGHRISK
	}
	public Alert(AlertType type, List<Vessel> vessels) {
		// XXX
	}
	public AlertType getType() {
		// XXX
		return AlertType.LOWRISK;
	}
	public List<Vessel> getVessels() {
		// XXX
		return new ArrayList<Vessel>();
	}
}
