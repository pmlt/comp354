package vms.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import common.Vessel;
import common.Vessel.VesselType;

import vms.*;

public class RadarDisplay {
	JFrame _Frame;
	FilterPanel _FilterPanel;
	TablePanel _TablePanel;
	MapPanel _MapPanel;
	JTabbedPane _TabbedPane;
	
	MainGUI.UserIdentity _CurrentIdentity;
	
	public RadarDisplay() {
		final String VIEW[] = { "Table view", "Map view"};
		_Frame = new JFrame("Vessel Monitoring System");
		_Frame.setSize(700,600);
		_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_FilterPanel = new FilterPanel();
		_TablePanel = new TablePanel();
		_MapPanel = new MapPanel();
		
		_TabbedPane = new JTabbedPane();
		
		_TabbedPane.add(_TabbedPane, VIEW[0]);
		_TabbedPane.add(_MapPanel,VIEW[1]);
		_Frame.setJMenuBar(new MenuBar(_Frame));
		_Frame.add(_FilterPanel, BorderLayout.WEST);
		_Frame.add(_TabbedPane, BorderLayout.CENTER);
	}
	public void show(MainGUI.UserIdentity identity) {
		_CurrentIdentity = identity;
		if (_CurrentIdentity == MainGUI.UserIdentity.NORMAL) {
			_FilterPanel.setVisible(true);
		}
		else {
			_FilterPanel.setVisible(false);
		}
		_TablePanel.changeIdentity(identity);
		_Frame.setVisible(true);
	}
	
	public void refresh(List<Alert> alerts, List<Vessel> vessels) {
		vessels = filterData(vessels);
		_TablePanel.update(alerts, vessels);
		_MapPanel.update(alerts, vessels);
	}
	
	public List<Vessel> filterData(List<Vessel> vessels) {
		if (_CurrentIdentity == MainGUI.UserIdentity.NORMAL) {
			return vessels; //No filtering
		}
		List<Vessel> copy = new ArrayList<Vessel>();
		Map<VesselType, Boolean> filters = _FilterPanel.getActiveFilters();
		for (Vessel v : vessels) {
			if (filters.get(v.getType())) copy.add(v);
		}
		return vessels;
	}
}
