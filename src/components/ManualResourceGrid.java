package components;

import java.util.LinkedList;
import java.util.List;

import model.Row;

public class ManualResourceGrid extends BasicGrid {

	public ManualResourceGrid(String entityName, List<Row> rows) {
		super(entityName, rows);

		saveRowButton.setVisible(false);
		deleteRowButton.addActionListener(e -> deleteRow());
	}

	@Override
	public void deleteRow() {

		int[] rowIndexesArray = grid.getSelectedRows();

		if (rowIndexesArray.length == 0)
			return;

		List<Integer> rowIndexesForDelete = new LinkedList<Integer>();
		for (int i = 0; i < rowIndexesArray.length; ++i)
			if (!addedRowsIndexes.contains(rowIndexesArray[i] + 1))
				rowIndexesForDelete.add(rowIndexesArray[i]);

		for (int i = 0; i < rowIndexesArray.length; ++i)
			System.out.println(rowIndexesArray[i]);
		System.out.println(addedRowsIndexes);
		System.out.println(rowIndexesForDelete);

		if (rowIndexesForDelete.isEmpty())
			return;

		List<Row> rowsForDelete = new LinkedList<Row>();
		for (int i = 0; i < rowIndexesForDelete.size(); ++i)
			rowsForDelete.add(getRows().get(rowIndexesForDelete.get(i)));

		refreshGrid();
	}

}
