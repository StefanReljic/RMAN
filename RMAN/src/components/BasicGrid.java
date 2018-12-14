package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.MetaEntity;
import model.Row;
import services.OracleService;
import views.AddRowView;
import views.MainView;

public class BasicGrid extends AbstractGrid {

	private static final long serialVersionUID = 6016062016941548001L;
	private static final String BASIC_GRID_ADD = "Add";
	private static final String BASIC_GRID_REMOVE = "Remove";
	private static final String BASIC_GRID_SAVE = "Save";

	public BasicGrid(List<Row> rows) {

		if (rows == null || rows.size() == 0)
			return;

		Object[] columnNames = rows.get(0).getItems().keySet().stream().collect(Collectors.toList()).toArray();
		Object[][] objects = new Object[rows.size()][columnNames.length];

		for (int i = 0; i < rows.size(); ++i)
			for (int j = 0; j < columnNames.length; ++j)
				objects[i][j] = rows.get(i).getItems().get(columnNames[j]).getValue();

		this.grid = new JTable(objects, columnNames);
		this.rows = rows;
		createComponent();

	}

	@Override
	void createComponent() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(new Dimension(400, 500));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.grid.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		this.grid.setSize(new Dimension(1000, 500));

		add(getButtonLayout(), BorderLayout.LINE_START);
		add(this.grid.getTableHeader());
		add(this.grid);
	}

	public void refreshGrid() {

		if (rows == null || rows.size() == 0)
			return;

		Object[] columnNames = rows.get(0).getItems().keySet().stream().collect(Collectors.toList()).toArray();
		Object[][] objects = new Object[rows.size()][columnNames.length];

		for (int i = 0; i < rows.size(); ++i)
			for (int j = 0; j < columnNames.length; ++j)
				objects[i][j] = rows.get(i).getItems().get(columnNames[j]).getValue();

		Component[] panelComponents = getComponents();
		int i = 0;
		for (i = 0; i < panelComponents.length; ++i)
			if (panelComponents[i].equals(this.grid))
				break;

		this.grid = new JTable(objects, columnNames);
		remove(i);
		add(this.grid, i);
		updateUI();
	}

	private Component getButtonLayout() {

		JPanel buttonLayout = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();

		JButton addRowButton = new JButton(BASIC_GRID_ADD);
		addRowButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		addRowButton.addActionListener(e -> addRow());

		JButton deleteRowButton = new JButton(BASIC_GRID_REMOVE);
		deleteRowButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		deleteRowButton.addActionListener(e -> deleteRow());

		JButton saveRowButton = new JButton(BASIC_GRID_SAVE);
		saveRowButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		saveRowButton.addActionListener(e -> saveRow());

		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		buttonLayout.add(addRowButton, gbs);

		gbs.gridx = 1;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		buttonLayout.add(deleteRowButton, gbs);

		gbs.gridx = 2;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		buttonLayout.add(saveRowButton, gbs);
		buttonLayout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		return buttonLayout;
	}

	private void addRow() {

		AddRowView addRowView = new AddRowView(rows.get(0).getColumnTypes());
		addRowView.setVisible(true);
	}

	private void deleteRow() {

		int[] rowIds = grid.getSelectedRows();
		List<Row> rowsForDelete = new LinkedList<Row>();
		for (int i = 0; i < rowIds.length; ++i)
			rowsForDelete.add(getRows().get(rowIds[i]));

		ServiceInterface serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");
		MetaDescription metaDescription = MetaDescription.deserialize((byte[]) MainView.getMetaDescription().getValue());
		MetaEntity metaEntity = metaDescription.findMetaEntityByName(rowsForDelete.get(0).getTableName());
		List<String> metaEntityIds = metaEntity.getMetaIds().keySet().stream().collect(Collectors.toList());

		for (Row row : rowsForDelete) {
			serviceInterface.deleteObject(row, metaEntityIds);
			this.rows.remove(row);
		}
		refreshGrid();
	}

	private void saveRow() {

	}
}