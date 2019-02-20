package views.resource;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ManualResourceMetaEntityView extends JPanel {

	private static final String GENERAL_LABEL = "General";
	private static final String CONSTRAINTS_LABEL = "Constraints";

	private JTabbedPane entityTabs;
	private GeneralTab generalTab;
	private ConstraintsTab constraintsTab;

	public ManualResourceMetaEntityView() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(getWorkingPane());
	}

	private JPanel getWorkingPane() {

		entityTabs = new JTabbedPane();
		generalTab = new GeneralTab();
		constraintsTab = new ConstraintsTab();
		entityTabs.addTab(GENERAL_LABEL, generalTab);
		entityTabs.addTab(CONSTRAINTS_LABEL, constraintsTab);

		JPanel tabPanel = new JPanel();
		tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
		tabPanel.add(entityTabs);

		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
		finalPanel.add(tabPanel);

		return finalPanel;
	}

}
