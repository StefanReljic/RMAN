package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.nio.file.Watchable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import components.BasicGrid;
import components.MenuLine;
import components.MessageBox;
import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.MetaEntity;
import meta.model.MetaInfo;
import model.Item;
import model.Row;
import oracle.sql.BLOB;
import services.AbstractService;
import services.OracleService;

public class MainView extends Dialog {

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

	public MainView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		this.metaDescriptions = new HashMap<String, Item>();
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
			}
		});

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

			byte[] bytes = ((BLOB) metaDescriptions.get(selectedInformatioResource.getTableName()).getValue()).getBytes();
			MetaDescription metaDescription = MetaDescription.deserialize(bytes);
			if (metaDescription != null) {

				ServiceInterface serviceInterface = interfaces.get(metaDescription.getMetaInfo().getResourceId());

			} else {
				MessageBox messageBox = new MessageBox(new JFrame(), METADESCRIPTION_DOES_NOT_EXIST);
				messageBox.setVisible(true);
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

	private List<Object> getInformationResourcesIds(List<Row> informationResources) {

		List<HashMap<String, Item>> listOfItems = informationResources.stream().map(ir -> ir.getItems()).collect(Collectors.toList());
		List<Object> ids = listOfItems.stream().map(i -> i.get("id").getValue()).collect(Collectors.toList());

		return ids;
	}

}
