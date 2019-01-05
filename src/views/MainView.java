package views;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import application.Config;
import components.MenuLine;
import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.ChildParrentModel;
import model.Row;
import services.OracleService;

public class MainView extends JDialog {

	private static final long serialVersionUID = -8712372762807225105L;

	private Map<String, ServiceInterface> interfaces;
	private Hashtable<String, Row> informationResources;
	private MetaDescription selectedInformationResourceMetaDescription;
	private List<ChildParrentModel> childParrentModels;

	private ServiceInterface serviceInterface;

	private static Map<String, MetaDescription> metaDescriptions;
	private static String selectedIrName;
	public static Row selectedInformationResource;
	public static ServiceInterface selectedInterface;

	private JFrame parrent;
	private MenuLine menu;
	private JSplitPane splitPane;
	private JScrollPane informationResourcesScrollPane;
	private JScrollPane informationResourceScrollPane;

	public MainView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		this.interfaces = new LinkedHashMap<String, ServiceInterface>();

		metaDescriptions = new LinkedHashMap<String, MetaDescription>();
		selectedInformationResource = null;
		selectedInterface = null;

		setInformationResources(new BigDecimal(1));
		setInterfaces();

		JTree informationResourceTree = new JTree(getNodes());
		informationResourceTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				if (informationResourceTree.getSelectionPath().getLastPathComponent().toString().trim().equals(""))
					return;

				selectedInterface = interfaces.get(informationResourceTree.getSelectionPath().getPathComponent(1).toString());
				selectedIrName = informationResourceTree.getSelectionPath().getPathComponent(1).toString();

				if (informationResourceTree.getSelectionPath().getPathCount() == 2) {

					selectedInformationResource = informationResources
							.get(informationResourceTree.getSelectionPath().getLastPathComponent().toString());
					selectedInformationResourceMetaDescription = metaDescriptions
							.get(informationResourceTree.getSelectionPath().getPathComponent(1).toString());
					childParrentModels = selectedInformationResourceMetaDescription.getParrentChildModels();

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) informationResourceTree.getLastSelectedPathComponent();
					for (ChildParrentModel model : childParrentModels)
						selectedNode.add(new DefaultMutableTreeNode(model.getChild().getEntityName()));

					informationResourceTree.expandPath(informationResourceTree.getSelectionPath());
					informationResourcesScrollPane.setSize(200, 800);
					splitPane.setDividerLocation(200);

				} else {

					String name = informationResourceTree.getSelectionPath().getLastPathComponent().toString();
					ChildParrentModel childParrentModel = childParrentModels.stream().filter(model -> model.getChild().getEntityName().equals(name))
							.findFirst().get();
					ChildParrentView childParrentView = new ChildParrentView(childParrentModel);
					informationResourceScrollPane.setViewportView(childParrentView);
					informationResourceScrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
						public void componentResized(ComponentEvent e) {
							childParrentView.setSize(informationResourceScrollPane.getSize());
							revalidate();
						}
					});
				}
			}
		});

		informationResourcesScrollPane = new JScrollPane(informationResourceTree);
		informationResourcesScrollPane.setSize(informationResourceTree.getWidth() + 100, 800);
		informationResourceScrollPane = new JScrollPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane, informationResourceScrollPane);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(menu, BorderLayout.PAGE_START);
		panel.add(splitPane, BorderLayout.PAGE_START);

		setSize(900, 500);
		add(panel);
		setLocationRelativeTo(this.parrent);

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

	private DefaultMutableTreeNode getNodes() {

		DefaultMutableTreeNode top = new DefaultMutableTreeNode();
		List<String> keys = informationResources.keySet().stream().collect(Collectors.toList());
		for (String key : keys)
			top.add(new DefaultMutableTreeNode(key));

		return top;
	}

	private void setInformationResources(BigDecimal userId) {

		informationResources = new Hashtable<String, Row>();
		serviceInterface = new OracleService(Config.getProperty("user"), Config.getProperty("password"), Config.getProperty("host"),
				Integer.parseInt(Config.getProperty("port")), Config.getProperty("resourceId"));

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
					this.informationResources.put(ir.getItems().get("name").getValue().toString(), ir);
					metaDescriptions.put(ir.getItems().get("name").getValue().toString(),
							MetaDescription.deserialize((byte[]) ir.getItems().get("meta_description").getValue()));
					break;
				}
			}
		}
	}

	private void setInterfaces() {

		List<String> keys = metaDescriptions.keySet().stream().collect(Collectors.toList());
		for (String key : keys) {

			MetaDescription metaDescription = metaDescriptions.get(key);
			if (metaDescription.getMetaInfo().getType().toUpperCase().equals("ORACLE")) {
				interfaces.put(key,
						new OracleService(metaDescription.getMetaInfo().getUser(), metaDescription.getMetaInfo().getPassword(),
								metaDescription.getMetaInfo().getHost(), metaDescription.getMetaInfo().getPort(),
								metaDescription.getMetaInfo().getResourceId()));
			}
		}
	}

	private List<Row> getUserInformationResourceIds(BigDecimal userId) {

		List<String> columns = new LinkedList<String>();
		columns.add("information_resource_id");
		Map<String, Object> conditions = new LinkedHashMap<String, Object>();
		conditions.put("user_id", userId);

		return serviceInterface.readObjects("u_ir", columns, conditions);
	}

	public static MetaDescription getMetaDescription() {

		return metaDescriptions.get(selectedIrName);
	}
}