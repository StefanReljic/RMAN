package components;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.Row;

public abstract class AbstractGrid extends JPanel {

	private static final long serialVersionUID = -8575992417939160651L;

	protected List<Row> rows;
	protected JTable grid;

	public List<Row> getRows() {
		return rows;
	}

	public JTable getGrid() {
		return grid;
	}

	public void setGrid(JTable grid) {
		this.grid = grid;
	}
	
	abstract void createComponent();

	@Override
	public String toString() {

		String string = "";
		
		if (rows == null)
			return null;
		
		for (Row row : rows)
			string += row.toString() + "\n";

		return string;
	}

}
