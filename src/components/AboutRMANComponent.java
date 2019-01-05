package components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutRMANComponent extends JDialog {

	private static final long serialVersionUID = 6578158697574213700L;

	public static final String TITLE = "Resource Manager";
	public static final String VERSION_LABEL = "Version";
	public static final String VERSION_VALUE = " 1.0";

	public AboutRMANComponent(JFrame parrent) {
		super(parrent);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();

		JLabel title = new JLabel(TITLE);
		JLabel versionLabel = new JLabel(VERSION_LABEL + ":" + VERSION_VALUE);

		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.weightx = 1;
		panel.add(title, gbs);

		gbs.gridx = 0;
		gbs.gridy = 1;
		gbs.gridwidth = 1;
		panel.add(versionLabel, gbs);

		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(parrent);
		setSize(200, 200);
		setResizable(false);

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