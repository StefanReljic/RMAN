package views.resource;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ManualResourceView extends Dialog {

	private static final String ADD_ENTITY_LABEL = "Add entity";

	private ManualResourceMetaInfoView manualResourceMetaInfoView;
	private JButton addEntityButton;
	private List<ManualResourceMetaEntityView> manualResourceMetaEntityViews;

	private JPanel manualMetaEntityPanel;

	public ManualResourceView(JFrame parrent) {
		super(parrent);

		manualResourceMetaEntityViews = new LinkedList<ManualResourceMetaEntityView>();
		addEntityButton = new JButton(ADD_ENTITY_LABEL);
		addEntityButton.addActionListener(e -> addEntity());

		manualResourceMetaInfoView = new ManualResourceMetaInfoView();
		JPanel manualMetaInfoPanel = new JPanel();
		manualMetaInfoPanel.setLayout(new BorderLayout());
		manualMetaInfoPanel.add(manualResourceMetaInfoView);

		ManualResourceMetaEntityView entity = new ManualResourceMetaEntityView();
		manualMetaEntityPanel = new JPanel();
		manualMetaEntityPanel.setLayout(new BoxLayout(manualMetaEntityPanel, BoxLayout.Y_AXIS));
		manualMetaEntityPanel.add(entity);
		manualResourceMetaEntityViews.add(entity);

		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
		finalPanel.add(manualMetaInfoPanel);
		finalPanel.add(addEntityButton);
		finalPanel.add(manualMetaEntityPanel);

		add(finalPanel);
		addWindowListeners();
		setLocationRelativeTo(this);
		setSize(600, 300);
		// setResizable(false);
	}

	private void addEntity() {

		ManualResourceMetaEntityView entity = new ManualResourceMetaEntityView();
		manualMetaEntityPanel.add(entity);
		manualResourceMetaEntityViews.add(entity);
		manualMetaEntityPanel.updateUI();
	}

	private void addWindowListeners() {
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
				dispose();
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
}
