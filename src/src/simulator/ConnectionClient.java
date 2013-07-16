package simulator;

import java.io.*;
import java.net.*;

import org.protocols.Netstring;

import common.UpdateData;

public class ConnectionClient implements Closeable {
	private final int TIMEOUT = 2000; //Never wait more than 2 seconds
	private Socket _Socket;
	
	public ConnectionClient() {
		_Socket = new Socket();
	}
	
	public void connect(String host, int port) throws IOException {
		SocketAddress addr = new InetSocketAddress(host, port);
		_Socket.connect(addr, TIMEOUT);
	}
	
	public void sendUpdate(UpdateData data) throws IOException {
		String json = data.toJSON();
		Netstring.write(_Socket.getOutputStream(), json.getBytes());
	}
	
	public boolean isReady() {
		return _Socket.isConnected() && !_Socket.isClosed();
	}

	@Override
	public void close() throws IOException {
		if (!_Socket.isClosed()) _Socket.close();
	}
}
