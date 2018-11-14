package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;

public class BasicGrid extends AbstractGrid {

	public BasicGrid() {

		super();
		this.grid = new JTable();
		this.rows = new ArrayList<>();
	}

	@Override
	public void setRows(List<Row> rows) {

		this.rows = rows;
		setGridValues();
	}

	public void setGridValues() {

		for (int i = 0; i < this.rows.size(); ++i) {
			List<Item> rowItems = rows.get(i).getItems().values().stream().collect(Collectors.toList());

			for (int j = 0; j < rowItems.size(); ++j) {
				this.grid.setValueAt(rowItems.get(j), i, j);
			}
		}
	}

	public void setLayout() {

		super.getLayout().addLayoutComponent("grid", this.grid);
	}

}
