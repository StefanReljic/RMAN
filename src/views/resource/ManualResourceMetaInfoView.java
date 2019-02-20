package views.resource;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import meta.model.MetaInfo;

public class ManualResourceMetaInfoView extends JPanel {

	private static final String USER_LABEL = "Connection user";
	private static final String PASSWORD_LABEL = "Connection password";
	private static final String HOST_LABEL = "Host";
	private static final String PORT_LABEL = "Port";
	private static final String SERVICE_NAME_LABEL = "Service name";

	private JTextField userTextField;
	private JPasswordField passwordField;
	private JTextField hostTextField;
	private JTextField portTextField;
	private JTextField serviceNameTextField;

	public ManualResourceMetaInfoView() {
		super();
		setLayout(new GridBagLayout());
		add(getWorkingPanel());

	}

	public ManualResourceMetaInfoView(MetaInfo metaInfo) {
		super();
		setLayout(new GridBagLayout());
		add(getWorkingPanel());
		setMetaInfoValues(metaInfo);
	}

	private void setMetaInfoValues(MetaInfo metaInfo) {

		if (metaInfo == null)
			return;

		userTextField.setText(metaInfo.getUser());
		passwordField.setText(metaInfo.getPassword());
		hostTextField.setText(metaInfo.getHost());
		portTextField.setText(metaInfo.getPort() + "");
		serviceNameTextField.setText(metaInfo.getResourceId());
	}

	private JPanel getWorkingPanel() {

		GridBagConstraints gbc = new GridBagConstraints();

		JPanel userPanel = new JPanel(new GridBagLayout());
		JLabel userLabel = new JLabel(USER_LABEL + ": ");
		userTextField = new JTextField(30);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		userPanel.add(userLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		userPanel.add(userTextField, gbc);

		JPanel passwordPanel = new JPanel(new GridBagLayout());
		JLabel passwordLabel = new JLabel(PASSWORD_LABEL + ": ");
		passwordField = new JPasswordField(30);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		passwordPanel.add(passwordLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		passwordPanel.add(passwordField, gbc);

		JPanel hostPanel = new JPanel(new GridBagLayout());
		JLabel hostLabel = new JLabel(HOST_LABEL + ": ");
		hostTextField = new JTextField(30);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		hostPanel.add(hostLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		hostPanel.add(hostTextField, gbc);

		JPanel portPanel = new JPanel(new GridBagLayout());
		JLabel portLabel = new JLabel(PORT_LABEL + ": ");
		portTextField = new JTextField(30);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		portPanel.add(portLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		portPanel.add(portTextField, gbc);

		JPanel serviceNamePanel = new JPanel(new GridBagLayout());
		JLabel serviceNameLabel = new JLabel(SERVICE_NAME_LABEL + ": ");
		serviceNameTextField = new JTextField(30);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		serviceNamePanel.add(serviceNameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		serviceNamePanel.add(serviceNameTextField, gbc);

		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
		finalPanel.add(userPanel);
		finalPanel.add(passwordPanel);
		finalPanel.add(hostPanel);
		finalPanel.add(portPanel);
		finalPanel.add(serviceNamePanel);

		return finalPanel;
	}

}
