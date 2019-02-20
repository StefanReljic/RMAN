package views.resource;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.MessageBox;
import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.types.InformationResourceType;
import services.OracleService;

public class AutomaticResourceView extends Dialog {

	private static final String INFORMATION_RESOURCE_TYPE_LABEL = "Select information resource type";
	private static final String USER_LABEL = "Connection user";
	private static final String PASSWORD_LABEL = "Connection password";
	private static final String HOST_LABEL = "Host";
	private static final String PORT_LABEL = "Port";
	private static final String SERVICE_NAME_LABEL = "Service name";
	private static final String GENERATE_BUTTON_LABEL = "Generate";

	private JComboBox<InformationResourceType> informationResourceTypeComboBox;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JTextField hostTextField;
	private JTextField portTextField;
	private JTextField serviceNameTextField;
	private JFrame parrent;

	public AutomaticResourceView(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		addWindowListeners();
		setLayout(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();

		JLabel informationResourceTypeLabel = new JLabel(INFORMATION_RESOURCE_TYPE_LABEL);
		informationResourceTypeComboBox = new JComboBox<InformationResourceType>();
		for (InformationResourceType type : InformationResourceType.getInformationResourceTypes())
			informationResourceTypeComboBox.addItem(type);

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new FlowLayout());
		typePanel.setAlignmentX(LEFT_ALIGNMENT);
		typePanel.add(informationResourceTypeLabel);
		typePanel.add(informationResourceTypeComboBox);

		JPanel workingPanel = getWorkingPanel();

		JButton generateButton = new JButton(GENERATE_BUTTON_LABEL);
		generateButton.addActionListener(e -> generateMetaDescription());

		setLocationRelativeTo(this);
		setSize(500, 300);
		setResizable(false);
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 3;
		add(typePanel, gbs);
		gbs.gridx = 0;
		gbs.gridy = 1;
		gbs.gridwidth = 3;
		add(workingPanel, gbs);
		gbs.gridx = 0;
		gbs.gridy = 4;
		gbs.gridwidth = 1;
		add(generateButton, gbs);

	}

	/**
	 * Automatically reads meta description and puts it into Oracle database. Calls
	 * addInformationResource method from OracleService.
	 */
	private void generateMetaDescription() {

		String user = userTextField.getText().trim();
		String password = new String(passwordField.getPassword());
		String host = hostTextField.getText().trim();
		Integer port = portTextField.getText().trim() == null || portTextField.getText().trim().equals("") ? null
				: Integer.parseInt(portTextField.getText().trim());
		String serviceId = serviceNameTextField.getText().trim();

		ServiceInterface serviceInterface = null;
		if (informationResourceTypeComboBox.getSelectedItem().toString().equals("ORACLE")) {
			serviceInterface = new OracleService(user, password, host, port, serviceId);
		}

		if (!serviceInterface.validateAutomaticResourceViewInputFields(user, password, host, port, serviceId)) {
			MessageBox messageBox = new MessageBox(parrent, "All field must be valid");
			messageBox.setVisible(true);
			return;
		}

		MetaDescription metaDescription = serviceInterface.readInformationResourceDescription();
		OracleService.addInformationResource(metaDescription);
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
