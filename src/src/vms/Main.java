package vms;

import java.io.IOException;
import java.net.InetSocketAddress;

import vms.gui.*;

public class Main {
	final static private String HOSTNAME = "localhost";
	final static private int PORT = 11233;
	
	private static ConnectionServer _Server;
	private static RadarMonitor _Monitor;
	private static MainGUI _MainGUI;
	
	private static class CSThread extends Thread {
		@Override
		public void run() {
			try {
				_Server.start();
				//Server was stopped!
				_Server.close();
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
		try {
			_Server.bind(new InetSocketAddress(HOSTNAME, PORT));
		} catch (IOException e) {
			System.out.println("Could not bind to " + HOSTNAME + ":" + PORT + " : " + e.getMessage());
			System.exit(1);
		}
		
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
		_MainGUI = new MainGUI(_Server);
		
		// Register RadarDisplay instance as observer of RadarMonitor
		_Monitor.registerObserver(_MainGUI);
		
		// Show RadarDisplay window
		_MainGUI.start();
	}

}
