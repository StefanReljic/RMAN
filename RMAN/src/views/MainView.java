package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.commons.collections4.map.HashedMap;

import components.BasicGrid;
import components.MenuLine;
import components.MessageBox;
import interfaces.ServiceInterface;
import model.Row;
import services.OracleService;

public class MainView extends Dialog {

	private static final long serialVersionUID = -8712372762807225105L;

	private static final String NO_INFORMATION_RESOURCE_MESSAGE = "Information resource doesn't exist.";
	
	private JFrame parrent;
	private MenuLine menu;
	private List<BasicGrid> grids;

	public MainView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		//this.grids = getGrids();

		JScrollPane informationResourcesScrollPane = new JScrollPane();
		JScrollPane informationResourceScrollPane = new JScrollPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane,
				informationResourceScrollPane);

		JPanel panel = new JPanel();

		panel.add(menu);
		panel.setBounds(0, 0, 400, 200);

		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(this.parrent);
		pack();

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	private List<BasicGrid> getGrids() {

		String informationResource = "";
		int id = 1;
		
		ServiceInterface serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");

		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		
		List<Row> rows = serviceInterface.readObjects("information_resource", null, conditions);
		
		if(rows != null && rows.size() != 0) {
			
			
			
		}else {
			MessageBox messageBox = new MessageBox(new JFrame(), NO_INFORMATION_RESOURCE_MESSAGE);
			messageBox.setVisible(true);
		}
		
		return null;
	}

}
