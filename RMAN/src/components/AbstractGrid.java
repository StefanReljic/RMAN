package components;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import model.Row;

public abstract class AbstractGrid extends JComponent {

	private static final long serialVersionUID = -8575992417939160651L;

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
