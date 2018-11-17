package views;

import java.awt.Dialog;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import components.MenuLine;

public class MainWindow extends Dialog {

	private JFrame parrent;
	private MenuLine menu;

	public MainWindow(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		this.menu = new MenuLine();

		JScrollPane informationResourcesScrollPane = new JScrollPane();
		JScrollPane informationResourceScrollPane = new JScrollPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourceScrollPane, informationResourceScrollPane);

	}

}
