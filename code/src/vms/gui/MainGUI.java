package vms.gui;

import java.util.List;

import vms.Alert;
import vms.ConnectionServer;
import vms.RadarMonitor;

import common.Vessel;

public class MainGUI implements RadarMonitor.Observer {
	public enum UserIdentity {
		NORMAL_USER, 
		OPERATOR
	}
	private Login _Login;
	private RadarDisplay _Display;
	private ConnectionServer _Server;
	
	public MainGUI(ConnectionServer cs) {
		_Login = new Login(this);
		_Display = new RadarDisplay(this);
		_Server = cs;
	}


	@Override
	public void refresh(List<Alert> alerts, List<Vessel> vessels) {
		_Display.refresh(alerts, vessels);
	}
	
	public void start() {
		//First, show login
		showLogin();
	}
	
	public void showLogin() {
		_Login.show();
	}
	public void showRadar(UserIdentity identity) {
		_Display.show(identity);
	}
	public void stopServer() {
//		System.out.println("Stopping server");
		_Server.stop();
	}
}
