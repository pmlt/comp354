package vms;

import org.protocols.Netstring;
import java.net.*;
import java.nio.channels.*;
import java.util.*;
import java.io.Closeable;
import java.io.IOException;
import common.UpdateData;
import common.Vessel;

/*
 * Class: ConnectionServer
 * Listens to incoming connections and accepts them.
 * Expects radar data from clients. When receiving radar data
 * it will parse the data and notify all interested parties.
 */
public class ConnectionServer implements Closeable {
	public interface Observer {
		public void update(String id, Vessel.VesselType type, Coord newCoords, Course newCourse, Calendar timestamp);
		public void refresh(Calendar timestamp);
	}
	private static long DEFAULT_REFRESH = 500; //Refresh every half second by default
	
	private boolean _Continue;
	private long _RefreshTime;
	private Selector _Selector;
	private ServerSocketChannel _Channel;
	private List<Observer> _Observers;
	
	public ConnectionServer () {
		_RefreshTime = DEFAULT_REFRESH;
		_Observers = new ArrayList<Observer>();
	}
	
	public void setMinimumRefresh(long milliseconds) {
		_RefreshTime = milliseconds;
	}
	
	public long getMinimumRefresh() {
		return _RefreshTime;
	}
	
	public void registerObserver(Observer o) {
		if (!_Observers.contains(o)) _Observers.add(o);
	}
	
	public void unregisterObserver(Observer o) {
		_Observers.remove(o); //remove() doesn't fail if object not found 
	}
	
	public void refreshObservers(Calendar timestamp) {
		for (int i=0; i < _Observers.size(); i++) {
			_Observers.get(i).refresh(timestamp);
		}
	}
	
	public void updateObservers(String id, Vessel.VesselType type, Coord newCoords, Course newCourse, Calendar timestamp) {
		for (int i=0; i < _Observers.size(); i++) {
			_Observers.get(i).update(id, type, newCoords, newCourse, timestamp);
		}
	}
	
	public void bind(SocketAddress addr) throws IOException {
		_Selector = Selector.open();
		_Channel = ServerSocketChannel.open();
		_Channel.configureBlocking(false);
		_Channel.socket().bind(addr);
		_Channel.register(_Selector, SelectionKey.OP_ACCEPT);
	}
	
	public void start() throws IOException {
		if (_Selector == null) throw new IOException("Must bind before calling start()!");
		
		_Continue = true;
		while (_Continue) {
			//Wait for an event from the network...
			int num = _Selector.select(_RefreshTime);
			if (num == 0) {
				// No event happened but select() returned, we reached a timeout
				refreshObservers(Calendar.getInstance());
				continue; //Go again as long as _Continue is true
			}
			//Something's on the wire! Let's see what it is...
			Set<SelectionKey> selectedKeys = _Selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();
			
			while (it.hasNext()) {
				SelectionKey key = it.next();
				if (SelectionKey.OP_ACCEPT == (key.readyOps() & SelectionKey.OP_ACCEPT)) {
					//We have a new connection to accept
					SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
					sc.configureBlocking(false);
					//We are interested in data coming from this new connection
					sc.register(_Selector, SelectionKey.OP_READ);
				}
				else if (SelectionKey.OP_READ == (key.readyOps() & SelectionKey.OP_READ)) {
					//We have data coming from one of the accepted connections
					SocketChannel sc = (SocketChannel)key.channel();
					_read(sc);
				}
				//Event is now processed
				it.remove();
			}
		}
	}
	
	public void stop() throws IOException {
		_Continue = false;
	}

	@Override
	public void close() throws IOException {
		if (_Channel != null) {
			_Channel.close();
			_Channel = null;
		}
		if (_Selector != null) {
			_Selector.close();
			_Selector = null;
		}
	}
	
	private void _read(SocketChannel sc) throws IOException {
		Iterator<byte[]> it = Netstring.read(sc.socket().getInputStream(), 1024*1024);
		while (it.hasNext()) {
			byte[] json = it.next();
			UpdateData ud = UpdateData.fromJSON(new String(json));
			updateObservers(ud.Id, ud.Type, ud.Coordinates, ud.Course, ud.Timestamp);
		}
	}
}
