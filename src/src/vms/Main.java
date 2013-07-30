package vms;

import java.io.IOException;
import vms.gui.*;

public class Main {
	private static ConnectionServer _Server;
	private static RadarMonitor _Monitor;
	private static MainGUI _MainGUI;
	
	private static class CSThread extends Thread {
		@Override
		public void run() {
			try {
				_Server.start();
			} catch (IOException e) {
				System.out.println(
					"ConnectionServer threw an exception: " + e.getMessage());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// XXX TODO
		// In order:
		// Create ConnectionServer instance
		_Server = new ConnectionServer();
		
		// Create RadarMonitor instance
		_Monitor = new RadarMonitor();
		
		// Register RadarMonitor as observer of ConnectionServer
		_Server.registerObserver(_Monitor);
		
		// Spin a new thread, and run ConnectionServer::start() in it.
		Thread t = new CSThread();
		t.start();
		
		// Create vms.gui.Login instance, show the window
		//LoginGUI = new Login();
		//LoginGUI.show();
		
		// After user has logged in, create vms.gui.RadarDisplay instance
		_MainGUI = new MainGUI();
		
		// Register RadarDisplay instance as observer of RadarMonitor
		_Monitor.registerObserver(_MainGUI);
		
		// Show RadarDisplay window
		_MainGUI.start();
	}

}
