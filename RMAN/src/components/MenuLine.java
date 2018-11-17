package components;

import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuLine extends JComponent {

	private JMenuBar menuBar;

	public MenuLine() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem addInformationResourceItem = new JMenuItem("Add new information resource");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(addInformationResourceItem);
		fileMenu.add(exitItem);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem contextualHelpItem = new JMenuItem("Show Contextual Help");
		JMenuItem aboutApplicationItem = new JMenuItem("About RMAN");
		helpMenu.add(contextualHelpItem);
		helpMenu.add(aboutApplicationItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.add(menuBar);
		add(panel);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
