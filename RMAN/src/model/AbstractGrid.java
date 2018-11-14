package model;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

public abstract class AbstractGrid extends JComponent{

	protected List<Row> rows;
	protected JTable grid;
	
	public List<Row> getRows() {
		return rows;
	}

	public abstract void setRows(List<Row> rows);

	public JTable getGrid() {
		return grid;
	}

	public void setGrid(JTable grid) {
		this.grid = grid;
	}

}
