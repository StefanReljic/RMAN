package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * View for user registration.
 */
public class RegistrationDialog extends Dialog {

	private static final String USERNAME_LABEL = "Username";
	private static final String PASSWORD_LABEL = "Password";
	private static final String CONFIRM_PASSWORD_LABEL = "Confirm password";
	private static final String REGISTER_BUTTON_LABEL = "Register";
	private static final String REGISTRATION_LABEL = "Registration";

	private JFrame parrent;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JButton registerButton;

	public RegistrationDialog(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		usernameField = new JTextField(30);
		passwordField = new JPasswordField(30);
		confirmPasswordField = new JPasswordField(30);
		registerButton = new JButton(REGISTER_BUTTON_LABEL);
		registerButton.addActionListener(e -> register());

		JLabel usernameLabel = new JLabel(USERNAME_LABEL + ":");
		JLabel passwordLabel = new JLabel(PASSWORD_LABEL + ":");
		JLabel confirmPasswordLabel = new JLabel(CONFIRM_PASSWORD_LABEL + ":");

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();

		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		panel.add(usernameLabel, gbs);

		gbs.gridx = 1;
		gbs.gridy = 0;
		gbs.gridwidth = 2;
		panel.add(usernameField, gbs);

		gbs.gridx = 0;
		gbs.gridy = 1;
		gbs.gridwidth = 1;
		panel.add(passwordLabel, gbs);

		gbs.gridx = 1;
		gbs.gridx = 1;
		gbs.gridwidth = 2;
		panel.add(passwordField, gbs);

		gbs.gridx = 0;
		gbs.gridy = 2;
		gbs.gridwidth = 1;
		panel.add(confirmPasswordLabel, gbs);

		gbs.gridx = 1;
		gbs.gridy = 2;
		gbs.gridwidth = 2;
		panel.add(confirmPasswordField, gbs);

		gbs.gridx = 1;
		gbs.gridy = 3;
		gbs.gridwidth = 1;
		panel.add(registerButton, gbs);

		panel.setBounds(0, 0, 400, 200);
		setBounds(0, 0, 400, 200);
		add(panel, BorderLayout.CENTER);
		pack();
		setTitle(REGISTRATION_LABEL);
		setResizable(false);
		setLocationRelativeTo(this.parrent);

	}

	/**
	 * User registration function.
	 */
	public void register() {

		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());

		if (username == null || password == null || confirmPassword == null)
			return;

		if (!password.equals(confirmPassword))
			return;

		// registration logic
	}

}
