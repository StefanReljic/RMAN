package views.resource;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.ManualResourceGrid;
import model.Item;
import model.Row;

public class ConstraintsTab extends JPanel {

	private ManualResourceGrid constraintsGrid;

	public ConstraintsTab() {
		super();

		List<Row> rows = new LinkedList<Row>();
		Row row = new Row();
		Item propertyItem = new Item("Constraint name", null);
		Item propertyTypeItem = new Item("Constraint type", null);
		row.getItems().put("Constraint name", propertyItem);
		row.getItems().put("Constraint type", propertyTypeItem);
		rows.add(row);
		constraintsGrid = new ManualResourceGrid("", rows);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(constraintsGrid);
	}
}
