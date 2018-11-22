package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationResourcesScrollPane, informationResourceScrollPane);

		JPanel panel = new JPanel();

		panel.add(menu);
		panel.setBounds(0, 0, 400, 200);

		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(this.parrent);
		pack();

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	private List<BasicGrid> getGrids() {

		return null;
	}

}
