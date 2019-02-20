package views.resource;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AddResourceView extends Dialog {

	private static final String SELECT_META_DESCRIPTION_METHOD_LABEL = "Select add meta description method";
	private static final String UPLOAD_META_DESCRIPTION_LABEL = "Upload meta description json";
	private static final String AUTOMATIC_META_DESCRIPTION_LABEL = "Automatic meta description generator";
	private static final String MANUAL_META_DESCRIPTION_LABEL = "Manual meta description generator";
	private static final String NEXT_BUTTON_LABEL = "NEXT";

	private JRadioButton uploadMetaDescription;
	private JRadioButton automaticMetaDescription;
	private JRadioButton manualMetaDescription;
	private ButtonGroup buttonGroup;
	private JButton nextButton;
	private JFrame parrent;

	public AddResourceView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		addWindowListeners();
		nextButton = new JButton(NEXT_BUTTON_LABEL);
		nextButton.addActionListener(e -> next());
		buttonGroup = new ButtonGroup();

		uploadMetaDescription = new JRadioButton(UPLOAD_META_DESCRIPTION_LABEL);
		automaticMetaDescription = new JRadioButton(AUTOMATIC_META_DESCRIPTION_LABEL);
		manualMetaDescription = new JRadioButton(MANUAL_META_DESCRIPTION_LABEL);

		buttonGroup.add(uploadMetaDescription);
		buttonGroup.add(automaticMetaDescription);
		buttonGroup.add(manualMetaDescription);
		uploadMetaDescription.setSelected(true);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(new JLabel(SELECT_META_DESCRIPTION_METHOD_LABEL + ":"));
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(uploadMetaDescription);
		panel.add(automaticMetaDescription);
		panel.add(manualMetaDescription);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(nextButton);

		setLocationRelativeTo(this);
		setSize(400, 200);
		setResizable(false);
		add(panel);
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

	private void next() {

		if (uploadMetaDescription.isSelected()) {
			dispose();
			new UploadResourceView();
		} else if (automaticMetaDescription.isSelected()) {
			dispose();
			AutomaticResourceView view = new AutomaticResourceView(parrent);
			view.setVisible(true);
		} else if (manualMetaDescription.isSelected()) {
			dispose();
			ManualResourceView view = new ManualResourceView(parrent);
			view.setVisible(true);
		}
	}

}
