package views;

import java.awt.Component;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components.BasicGrid;
import interfaces.ServiceInterface;
import meta.model.ChildParrentModel;
import meta.model.MetaEntity;
import model.Row;
import services.OracleService;

public class ChildParrentView extends JPanel {

	private static final String CHILD_TITLE = "Child";
	private static final String PARRENT_TITLE = "Parrent";
	private ChildParrentModel parrentChildModel;

	private JTabbedPane parrentTabs;

	public ChildParrentView(ChildParrentModel parrentChildModel) {

		this.parrentChildModel = parrentChildModel;
		makeView();
	}

	private void makeView() {

		if (this.parrentChildModel == null)
			return;

		MetaEntity child = this.parrentChildModel.getChild();
		ServiceInterface serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");
		List<String> columns = child.getMetaRow().getItems().keySet().stream().collect(Collectors.toList());
		List<Row> rows = serviceInterface.readObjects(child.getEntityName(), columns, null);

		BasicGrid childGrid = new BasicGrid(rows);

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

		ServiceInterface serviceInterface = new OracleService("rman", "rman", "localhost", 1521, "testdb");
		List<String> columns = parrent.getMetaRow().getItems().keySet().stream().collect(Collectors.toList());
		List<Row> rows = serviceInterface.readObjects(parrent.getEntityName(), columns, null);

		BasicGrid parrentGrid = new BasicGrid(rows);

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
