package components;

import java.awt.LayoutManager;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;
import model.Row;

public class BasicGrid extends AbstractGrid {

	private static final long serialVersionUID = 6016062016941548001L;

	public BasicGrid(List<Row> rows) {

		if (rows == null || rows.size() == 0)
			return;

		Object[] columnNames = rows.get(0).getItems().keySet().stream().collect(Collectors.toList()).toArray();
		Object[][] objects = new Object[rows.size()][columnNames.length];

		for (int i = 0; i < rows.size(); ++i) {
			for (int j = 0; j < columnNames.length; ++j) {
				objects[i][j] = rows.get(i).getItems().get(columnNames[j]).getValue();
			}
		}
		this.grid = new JTable(objects, columnNames);

		add(this.grid);
	}

	@Override
	public LayoutManager getLayout() {

		return super.getLayout();
	}
}