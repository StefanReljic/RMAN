package views;

import java.awt.Dialog;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import components.BasicGrid;
import components.MenuLine;

public class MainWindow extends Dialog {

	private JFrame parrent;
	private MenuLine menu;
	private List<BasicGrid> grids;

	public MainWindow(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();
		this.grids = getGrids();

		JScrollPane informationResourcesScrollPane = new JScrollPane();
		JScrollPane informationResourceScrollPane = new JScrollPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane,
				informationResourceScrollPane);

	}

	private List<BasicGrid> getGrids() {

		return null;
	}

}
