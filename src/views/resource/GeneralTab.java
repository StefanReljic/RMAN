package views.resource;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.ManualResourceGrid;
import model.Item;
import model.Row;

public class GeneralTab extends JPanel {

	private static final String ENTITY_NAME_LABEL = "Entity name";

	private JTextField entityNameTextField;
	private ManualResourceGrid entityRowGrid;

	public GeneralTab() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel entityNameLabel = new JLabel(ENTITY_NAME_LABEL);
		entityNameTextField = new JTextField();
		JPanel entityNamePanel = new JPanel();
		entityNamePanel.setLayout(new BoxLayout(entityNamePanel, BoxLayout.X_AXIS));
		entityNamePanel.add(entityNameLabel);
		entityNamePanel.add(entityNameTextField);

		List<Row> rows = new LinkedList<Row>();
		Row row = new Row();
		Item propertyItem = new Item("Property name", null);
		Item propertyTypeItem = new Item("Property type", null);
		row.getItems().put("Property name", propertyItem);
		row.getItems().put("Property type", propertyTypeItem);
		rows.add(row);
		entityRowGrid = new ManualResourceGrid("", rows);

		add(entityNamePanel);
		add(entityRowGrid);
	}

}
