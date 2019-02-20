package views;

import java.awt.Component;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import components.BasicGrid;
import interfaces.ServiceInterface;
import meta.model.ChildParrentModel;
import meta.model.MetaEntity;
import model.Row;

public class ChildParrentView extends JPanel {

	private static final String CHILD_TITLE = "Child";
	private static final String PARRENT_TITLE = "Parrent";
	private ChildParrentModel parrentChildModel;

	private JTabbedPane parrentTabs;
	private BasicGrid parrentGrid;

	public ChildParrentView(ChildParrentModel parrentChildModel) {

		this.parrentChildModel = parrentChildModel;
		makeView();
	}

	private void makeView() {

		if (this.parrentChildModel == null)
			return;

		MetaEntity child = this.parrentChildModel.getChild();
		ServiceInterface serviceInterface = MainView.selectedInterface;
		List<String> columns = child.getMetaRow().getItems().keySet().stream().collect(Collectors.toList());
		List<Row> rows = serviceInterface.readObjects(child.getEntityName(), columns, null);

		BasicGrid childGrid = new BasicGrid(child.getEntityName(), rows);
		JPanel childPanel = new JPanel();
		childPanel.setLayout(new BoxLayout(childPanel, BoxLayout.Y_AXIS));
		childPanel.add(new JLabel(CHILD_TITLE + ": " + child.getEntityName()));
		childPanel.add(childGrid);

		if (parrentChildModel.getParrents().size() != 0) {
			this.parrentTabs = new JTabbedPane();
			for (MetaEntity metaEntity : parrentChildModel.getParrents())
				this.parrentTabs.addTab(metaEntity.getEntityName(), makeParrentView(metaEntity));
			parrentTabs.setSelectedIndex(0);
		}
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, childPanel, parrentTabs);
		add(splitPane);

	}

	private Component makeParrentView(MetaEntity parrent) {

		JPanel parrentPanel = new JPanel();
		ServiceInterface serviceInterface = MainView.selectedInterface;
		List<String> columns = parrent.getMetaRow().getItems().keySet().stream().collect(Collectors.toList());
		List<Row> rows = serviceInterface.readObjects(parrent.getEntityName(), columns, null);
		parrentGrid = new BasicGrid(parrent.getEntityName(), rows);
		parrentPanel.setLayout(new BoxLayout(parrentPanel, BoxLayout.Y_AXIS));
		parrentPanel.add(new JLabel(PARRENT_TITLE + ": " + parrent.getEntityName()));
		parrentPanel.add(parrentGrid);

		return parrentPanel;
	}

	public ChildParrentModel getParrentChildModel() {
		return parrentChildModel;
	}

	public void setParrentChildModel(ChildParrentModel parrentChildModel) {
		this.parrentChildModel = parrentChildModel;
	}
}