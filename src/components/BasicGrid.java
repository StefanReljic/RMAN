package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.common.io.Files;

import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.MetaEntity;
import model.Item;
import model.Row;
import utils.ExcelUtils;
import views.MainView;

public class BasicGrid extends AbstractGrid {

	private static final long serialVersionUID = 6016062016941548001L;
	private static final String BASIC_GRID_ADD = "Add";
	private static final String BASIC_GRID_REMOVE = "Remove";
	private static final String BASIC_GRID_SAVE = "Save";
	private static final String EXPORT = "Export";
	private static final String EXPORT_TO_EXCEL = "Excel";

	private List<Integer> addedRowsIndexes;
	private String entityName;
	private List<String> keys;

	public BasicGrid(String entityName, List<Row> rows) {

		if (rows == null || rows.size() == 0)
			return;

		this.entityName = entityName;
		this.keys = rows.get(0).getItems().keySet().stream().collect(Collectors.toList());

		Object[] columnNames = rows.get(0).getItems().keySet().stream().collect(Collectors.toList()).toArray();
		Object[][] objects = new Object[rows.size()][columnNames.length];

		for (int i = 0; i < rows.size(); ++i)
			for (int j = 0; j < columnNames.length; ++j)
				objects[i][j] = rows.get(i).getItems().get(columnNames[j]).getValue();

		DefaultTableModel defaultTableModel = new DefaultTableModel();
		for (Object columnName : columnNames)
			defaultTableModel.addColumn(columnName);

		for (Object[] row : objects)
			defaultTableModel.addRow(row);

		this.grid = new JTable(defaultTableModel);
		this.rows = rows;
		this.addedRowsIndexes = new LinkedList<Integer>();
		createComponent();

	}

	@Override
	protected void createComponent() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(new Dimension(400, 500));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.grid.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		this.grid.setSize(new Dimension(1000, 500));
		this.grid.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

		DefaultTableModel defaultTableModel = new DefaultTableModel(objects, columnNames);

		Component[] panelComponents = getComponents();
		int i = 0;
		for (i = 0; i < panelComponents.length; ++i)
			if (panelComponents[i].equals(this.grid))
				break;

		this.grid = new JTable(defaultTableModel);
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

		gbs.gridx = 3;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		buttonLayout.add(createExportMenu(), gbs);

		buttonLayout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		return buttonLayout;
	}

	private void addRow() {

		DefaultTableModel model = (DefaultTableModel) grid.getModel();
		Object[] nullObjects = new Object[model.getColumnCount()];
		for (int i = 0; i < model.getColumnCount(); ++i)
			nullObjects[i] = "";

		model.addRow(nullObjects);
		addedRowsIndexes.add(model.getRowCount());
		addedRowsIndexes = addedRowsIndexes.stream().sorted().collect(Collectors.toList());
	}

	private void deleteRow() {

		int[] rowIndexesArray = grid.getSelectedRows();

		if (rowIndexesArray.length == 0)
			return;

		List<Integer> rowIndexesForDelete = new LinkedList<Integer>();
		for (int i = 0; i < rowIndexesArray.length; ++i)
			if (!addedRowsIndexes.contains(rowIndexesArray[i] + 1))
				rowIndexesForDelete.add(rowIndexesArray[i]);

		if (rowIndexesForDelete.isEmpty())
			return;

		List<Row> rowsForDelete = new LinkedList<Row>();
		for (int i = 0; i < rowIndexesForDelete.size(); ++i)
			rowsForDelete.add(getRows().get(rowIndexesForDelete.get(i)));

		ServiceInterface serviceInterface = MainView.selectedInterface;
		MetaDescription metaDescription = MainView.getMetaDescription();
		MetaEntity metaEntity = metaDescription.findMetaEntityByName(rowsForDelete.get(0).getTableName());
		List<String> metaEntityIds = metaEntity.getMetaIds().keySet().stream().collect(Collectors.toList());

		for (Row row : rowsForDelete) {
			serviceInterface.deleteObject(row, metaEntityIds);
			this.rows.remove(row);
		}
		refreshGrid();
	}

	private void saveRow() {

		if (addedRowsIndexes.size() == 0)
			return;

		if (this.grid.isEditing())
			this.grid.getCellEditor().stopCellEditing();

		List<Row> rowsForSave = new LinkedList<Row>();
		DefaultTableModel defaultTableModel = (DefaultTableModel) grid.getModel();
		int numberOfColumns = defaultTableModel.getColumnCount();

		for (Integer i : addedRowsIndexes) {

			int counter = 0;
			for (int j = 0; j < numberOfColumns; ++j)
				if (defaultTableModel.getValueAt(i - 1, j) != null && !defaultTableModel.getValueAt(i - 1, j).equals(""))
					counter++;

			if (counter == numberOfColumns) {

				Row row = new Row();
				row.setTableName(entityName);

				for (int j = 0; j < numberOfColumns; ++j) {
					Item item = new Item("", defaultTableModel.getValueAt(i - 1, j));
					row.getItems().put(keys.get(j), item);
				}

				rowsForSave.add(row);
				rows.add(row);
			}
		}

		ServiceInterface serviceInterface = MainView.selectedInterface;

		for (Row row : rowsForSave)
			try {
				serviceInterface.addObject(row);
			} catch (Exception e) {
				e.printStackTrace();
			}
		addedRowsIndexes.clear();
		refreshGrid();
	}

	private JMenuBar createExportMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu export = new JMenu(EXPORT);
		export.setVisible(true);
		JMenuItem excelExport = new JMenuItem(EXPORT_TO_EXCEL);
		excelExport.addActionListener(e -> exportToExcel());
		export.add(excelExport);

		menuBar.add(export);

		return menuBar;
	}

	private void exportToExcel() {

		ExcelUtils excelUtils = new ExcelUtils();
		byte[] excel = excelUtils.exportToByteArray(rowsToMatrix());

		JFileChooser fileChooser = new JFileChooser();
		int retrival = fileChooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try {
				Files.write(excel, new File(fileChooser.getSelectedFile() + ".xlsx"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected Object[][] rowsToMatrix() {

		if (rows == null || rows.size() == 0)
			return null;

		List<String> keys = rows.get(0).getItems().keySet().stream().collect(Collectors.toList());
		Object[][] matrix = new Object[rows.size() + 1][keys.size()];

		for (int j = 0; j < keys.size(); ++j)
			matrix[0][j] = keys.get(j);

		for (int i = 1; i < rows.size() + 1; ++i)
			for (int j = 0; j < keys.size(); ++j)
				matrix[i][j] = rows.get(i - 1).getItems().get(keys.get(j)).getValue();

		return matrix;
	}

	public String getEntityName() {
		return entityName;
	}
}