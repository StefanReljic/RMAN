package components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import views.HelpView;

public class MenuLine extends JPanel {

	private static final long serialVersionUID = -7997923460651107314L;

	private JMenuBar menuBar;

	public static final String FILE_LABEL = "File";
	public static final String NEW_RESOURCE_LABEL = "Add new information resource";
	public static final String EXIT_LABEL = "Exit";
	public static final String HELP_LABEL = "Help";
	public static final String CONTEXTUAL_HELP_LABEL = "Show Contextual Help";
	public static final String ABOUT_LABEL = "About RMAN";

	public MenuLine() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(FILE_LABEL);
		JMenuItem addInformationResourceItem = new JMenuItem(NEW_RESOURCE_LABEL);
		JMenuItem exitItem = new JMenuItem(EXIT_LABEL);
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
		fileMenu.add(addInformationResourceItem);
		fileMenu.add(exitItem);

		JMenu helpMenu = new JMenu(HELP_LABEL);
		JMenuItem contextualHelpItem = new JMenuItem(CONTEXTUAL_HELP_LABEL);
		contextualHelpItem.addActionListener(e -> {
			HelpView helpView = new HelpView(new JFrame());
			helpView.setVisible(true);
		});
		JMenuItem aboutApplicationItem = new JMenuItem(ABOUT_LABEL);
		aboutApplicationItem.addActionListener(e -> {

			AboutRMANComponent about = new AboutRMANComponent(new JFrame());
			about.setVisible(true);
		});
		helpMenu.add(contextualHelpItem);
		helpMenu.add(aboutApplicationItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 3;
		panel.add(menuBar, gbs);

		add(panel);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
