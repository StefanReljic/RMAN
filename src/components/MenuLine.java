<<<<<<< HEAD:RMAN/src/components/MenuLine.java
package components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import views.HelpView;
import views.resource.AddResourceView;

public class MenuLine extends JPanel {

	private static final long serialVersionUID = -7997923460651107314L;

	private JMenuBar menuBar;

	public static final String FILE_LABEL = "File";
	public static final String NEW_RESOURCE_LABEL = "Add new information resource";
	public static final String EXIT_LABEL = "Exit";
	public static final String HELP_LABEL = "Help";
	public static final String CONTEXTUAL_HELP_LABEL = "Show Contextual Help";
	public static final String ABOUT_LABEL = "About RMAN";

	public MenuLine() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(FILE_LABEL);
		JMenuItem addInformationResourceItem = new JMenuItem(NEW_RESOURCE_LABEL);
		addInformationResourceItem.addActionListener(e -> {
			JFrame frame = new JFrame();
			AddResourceView addResourceView = new AddResourceView(frame);
			addResourceView.setVisible(true);
		});

		JMenuItem exitItem = new JMenuItem(EXIT_LABEL);
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
		fileMenu.add(addInformationResourceItem);
		fileMenu.add(exitItem);

		JMenu helpMenu = new JMenu(HELP_LABEL);
		JMenuItem contextualHelpItem = new JMenuItem(CONTEXTUAL_HELP_LABEL);
		contextualHelpItem.addActionListener(e -> {
			HelpView helpView = new HelpView(new JFrame());
			helpView.setVisible(true);
		});
		JMenuItem aboutApplicationItem = new JMenuItem(ABOUT_LABEL);
		aboutApplicationItem.addActionListener(e -> {

			AboutRMANComponent about = new AboutRMANComponent(new JFrame());
			about.setVisible(true);
		});
		helpMenu.add(contextualHelpItem);
		helpMenu.add(aboutApplicationItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 3;
		panel.add(menuBar, gbs);

		add(panel);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
=======
package components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import application.Config;
import interfaces.ServiceInterface;
import meta.converters.MetaDescriptionConverter;
import meta.model.MetaDescription;
import model.Row;
import model.Item;
import services.OracleService;
import views.HelpView;

public class MenuLine extends JPanel {

	private static final long serialVersionUID = -7997923460651107314L;

	private JMenuBar menuBar;

	public static final String FILE_LABEL = "File";
	public static final String NEW_RESOURCE_LABEL = "Add new information resource";
	public static final String EXIT_LABEL = "Exit";
	public static final String HELP_LABEL = "Help";
	public static final String CONTEXTUAL_HELP_LABEL = "Show Contextual Help";
	public static final String ABOUT_LABEL = "About RMAN";
	public static final String CANNOT_ADD_INFORMATION_RESOURCE_MESSAGE = "Cannot add information resource";

	public MenuLine() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(FILE_LABEL);
		JMenuItem addInformationResourceItem = new JMenuItem(NEW_RESOURCE_LABEL);
		addInformationResourceItem.addActionListener(e -> {

			JFileChooser metaDescriptorChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
			metaDescriptorChooser.setFileFilter(filter);
			metaDescriptorChooser.showOpenDialog(this);

			if (metaDescriptorChooser.getSelectedFile() == null)
				return;

			MetaDescriptionConverter metaDescriptionConverter = new MetaDescriptionConverter();
			MetaDescription metaDescription = null;
			try {
				metaDescription = metaDescriptionConverter.fileToMetaDescription(metaDescriptorChooser.getSelectedFile());
			} catch (IOException e1) {
				MessageBox messageBox = new MessageBox(new JFrame(), e1.getMessage());
				messageBox.setVisible(true);
			}
			if (metaDescription != null) {

				byte[] metaDescriptionBytes = null;
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream out = new ObjectOutputStream(bos);

					out.writeObject(metaDescription);
					out.close();
					metaDescriptionBytes = bos.toByteArray();
					bos.close();
				} catch (IOException ex) {
					MessageBox messageBox = new MessageBox(new JFrame(), ex.getMessage());
					messageBox.setVisible(true);
				}

				ServiceInterface serviceInterface = new OracleService(Config.getProperty("user"), Config.getProperty("password"), Config.getProperty("host"),
						Integer.parseInt(Config.getProperty("port")), Config.getProperty("resourceId"));
				List<String> idColumns = new LinkedList<String>();
				idColumns.add("INFORMATION_RESOURCE_SEQ.NEXTVAL");
				List<Row> rows = serviceInterface.readObjects("dual", idColumns, null);
				BigDecimal nextId = (BigDecimal) rows.get(0).getItems().get("INFORMATION_RESOURCE_SEQ.NEXTVAL").getValue();

				Row row = new Row();
				row.setTableName("INFORMATION_RESOURCE");
				Map<String, Item> items = new LinkedHashMap<String, Item>();

				Item idItem = new Item("BigDecimal", nextId);
				items.put("ID", idItem);

				Item userItem = new Item("String", metaDescription.getMetaInfo().getUser());
				items.put("CONN_USER", userItem);

				Item passwordItem = new Item("String", metaDescription.getMetaInfo().getPassword());
				items.put("CONN_PASSWORD", passwordItem);

				Item hostItem = new Item("String", metaDescription.getMetaInfo().getHost());
				items.put("HOST", hostItem);

				Item portItem = new Item("int", metaDescription.getMetaInfo().getPort());
				items.put("PORT", portItem);

				Item resourceIdItem = new Item("String", metaDescription.getMetaInfo().getResourceId());
				items.put("NAME", resourceIdItem);

				Item metaDescriptionBytesItem = new Item("byte[]", metaDescriptionBytes);
				items.put("META_DESCRIPTION", metaDescriptionBytesItem);

				row.setItems(items);

				Item uir_ir = new Item("BigDecimal", nextId);
				Item uir_user = new Item("BigDecimal", 1);
				Row uirRow = new Row();
				Map<String, Item> uirItems = new LinkedHashMap<String, Item>();
				uirItems.put("INFORMATION_RESOURCE_ID", uir_ir);
				uirItems.put("USER_ID", uir_user);
				uirRow.setItems(uirItems);
				uirRow.setTableName("U_IR");

				try {
					serviceInterface.addObject(row);
					serviceInterface.addObject(uirRow);
				} catch (SQLException e1) {
					MessageBox messageBox = new MessageBox(new JFrame(), CANNOT_ADD_INFORMATION_RESOURCE_MESSAGE);
					messageBox.setVisible(true);
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		JMenuItem exitItem = new JMenuItem(EXIT_LABEL);
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
		fileMenu.add(addInformationResourceItem);
		fileMenu.add(exitItem);

		JMenu helpMenu = new JMenu(HELP_LABEL);
		JMenuItem contextualHelpItem = new JMenuItem(CONTEXTUAL_HELP_LABEL);
		contextualHelpItem.addActionListener(e -> {
			HelpView helpView = new HelpView(new JFrame());
			helpView.setVisible(true);
		});
		JMenuItem aboutApplicationItem = new JMenuItem(ABOUT_LABEL);
		aboutApplicationItem.addActionListener(e -> {

			AboutRMANComponent about = new AboutRMANComponent(new JFrame());
			about.setVisible(true);
		});
		helpMenu.add(contextualHelpItem);
		helpMenu.add(aboutApplicationItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 3;
		panel.add(menuBar, gbs);

		add(panel);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
>>>>>>> 78340d376cff8fb635393e55868dec34a1bf4fe2:src/components/MenuLine.java
}