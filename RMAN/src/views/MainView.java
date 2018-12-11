package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import components.BasicGrid;
import components.MenuLine;
import components.MessageBox;
import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.MetaEntity;
import model.Item;
import model.Row;
import services.OracleService;

public class MainView extends JDialog {

	private static final long serialVersionUID = -8712372762807225105L;

	private static final String NO_INFORMATION_RESOURCE_MESSAGE = "Information resource doesn't exist.";
	private static final String METADESCRIPTION_DOES_NOT_EXIST = "Metadescription does not exist";

	private JFrame parrent;
	private MenuLine menu;
	private Map<String, ServiceInterface> interfaces;
	private Map<String, Item> metaDescriptions;
	private Hashtable<String, Row> informationResources;
	private Row selectedInformationResource;
	private List<BasicGrid> grids;

	private ServiceInterface serviceInterface;

	private JSplitPane splitPane;
	private JScrollPane informationResourcesScrollPane;
	private JScrollPane informationResourceScrollPane;

	public MainView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		this.metaDescriptions = new HashMap<String, Item>();
		this.grids = new LinkedList<BasicGrid>();
		interfaces = new HashMap<String, ServiceInterface>();
		this.selectedInformationResource = null;

		setInterfaces();
		setInformationResources(new BigDecimal(1));

		JTree informationResourceTree = new JTree(informationResources);
		informationResourceTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				selectedInformationResource = informationResources.get(e.getNewLeadSelectionPath().getLastPathComponent());
				setGrids();

				for (BasicGrid grid : grids)
					informationResourceScrollPane.add(grid);
			}
		});

		informationResourcesScrollPane = new JScrollPane(informationResourceTree);
		informationResourcesScrollPane.setSize(informationResourceTree.getWidth() + 100, 800);
		

		informationResourceScrollPane = new JScrollPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane, informationResourceScrollPane);
		splitPane.setSize(1000, 400);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));		
		panel.add(menu, BorderLayout.LINE_START);
		panel.add(splitPane);

		setSize( 5000, 1000);
		add(panel);
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
		serviceInterface = interfaces.get("information_resource");

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
					this.metaDescriptions.put(ir.getTableName(), ir.getItems().get("meta_description"));
					break;
				}
			}
		}
	}

	private void setInterfaces() {

		interfaces.put("information_resource", new OracleService("rman", "rman", "localhost", 1521, "testdb"));
	}

	private void setGrids() {

		Row selectedInformatioResource = informationResources.get("information_resource");

		if (selectedInformatioResource != null) {

			byte[] bytes = (byte[]) metaDescriptions.get(selectedInformatioResource.getTableName()).getValue();
			MetaDescription metaDescription = MetaDescription.deserialize(bytes);
			if (metaDescription != null) {

				ServiceInterface serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");
				List<MetaEntity> metaEntities = metaDescription.getMetaEntities();
				for (MetaEntity metaEntity : metaEntities) {

					List<String> columns = metaEntity.getMetaRow().getItems().keySet().stream().collect(Collectors.toList());
					List<Row> rows = serviceInterface.readObjects(metaEntity.getEntityName(), columns, null);
					grids.add(new BasicGrid(rows));
				}

			} else {
				MessageBox messageBox = new MessageBox(new JFrame(), METADESCRIPTION_DOES_NOT_EXIST);
				messageBox.setVisible(true);
			}

		} else {
			MessageBox messageBox = new MessageBox(new JFrame(), NO_INFORMATION_RESOURCE_MESSAGE);
			messageBox.setVisible(true);
		}
	}

	private List<Row> getUserInformationResourceIds(BigDecimal userId) {

		List<String> columns = new LinkedList<String>();
		columns.add("information_resource_id");
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("user_id", userId);

		return serviceInterface.readObjects("u_ir", columns, conditions);
	}

	private List<Object> getInformationResourcesIds(List<Row> informationResources) {

		List<HashMap<String, Item>> listOfItems = informationResources.stream().map(ir -> ir.getItems()).collect(Collectors.toList());
		List<Object> ids = listOfItems.stream().map(i -> i.get("id").getValue()).collect(Collectors.toList());

		return ids;
	}

}
