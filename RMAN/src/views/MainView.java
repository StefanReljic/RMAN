package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.text.html.ObjectView;

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
	private Hashtable<String, Row> informationResources;
	private List<BasicGrid> grids;

	private ServiceInterface serviceInterface;

	public MainView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		setInformationResources(new BigDecimal(1));
		// setGrids();

		System.out.println(informationResources);
		JTree informationResourceTree = new JTree(informationResources);
		JScrollPane informationResourcesScrollPane = new JScrollPane(informationResourceTree);

		JScrollPane informationResourceScrollPane = new JScrollPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane, informationResourceScrollPane);
		splitPane.setBounds(0, 0, 400, 400);

		JPanel panel = new JPanel();

		panel.add(menu);
		panel.setBounds(0, 0, 400, 400);
		panel.add(splitPane);

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

	private void setInformationResources(BigDecimal userId) {

		informationResources = new Hashtable<String, Row>();
		serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");

		List<Row> userInformationResourceIds = getUserInformationResourceIds(new BigDecimal(1));

		List<String> columns = new LinkedList<String>();
		columns.add("id");
		columns.add("conn_user");
		columns.add("conn_password");
		columns.add("host");
		columns.add("port");
		columns.add("name");
		columns.add("meta_description");
		List<Row> informationResources = serviceInterface.readObjects("information_resource", columns, null);
		for (Row id : userInformationResourceIds) {

			for (Row ir : informationResources) {
				if (ir.getItems().get("id").equals(id.getItems().get("information_resource_id"))) {
					this.informationResources.put(ir.getTableName(), ir);
					break;
				}
			}
		}
	}

	private void setGrids() {

		String informationResource = "";
		int id = 1;

		serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");
		List<Row> userInformationResourceIds = getUserInformationResourceIds(new BigDecimal(1));// logged in user id
		List<Row> informationResources = serviceInterface.readObjects("information_resource", null, null);

		if (userInformationResourceIds != null && userInformationResourceIds.size() != 0) {

			for (Row row : userInformationResourceIds) {

				BigDecimal irId = (BigDecimal) row.getItems().get("NUMBER").getValue();

			}

		} else {
			MessageBox messageBox = new MessageBox(new JFrame(), NO_INFORMATION_RESOURCE_MESSAGE);
			messageBox.setVisible(true);
		}
	}

	private BasicGrid newBasicGrid(Row informationResourceId) {

		BasicGrid basicGrid = new BasicGrid();

		return basicGrid;
	}

	private List<Row> getUserInformationResourceIds(BigDecimal userId) {

		List<String> columns = new LinkedList<>();
		columns.add("information_resource_id");
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("user_id", userId);

		return serviceInterface.readObjects("u_ir", columns, conditions);
	}

}
