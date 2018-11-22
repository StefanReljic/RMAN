package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.MessageBox;
import model.User;
import services.UserService;

/**
 * View for user registration.
 */
public class RegistrationDialog extends Dialog {

	private static final String USERNAME_LABEL = "Username";
	private static final String PASSWORD_LABEL = "Password";
	private static final String CONFIRM_PASSWORD_LABEL = "Confirm password";
	private static final String REGISTER_BUTTON_LABEL = "Register";
	private static final String REGISTRATION_LABEL = "Registration";
	private static final String FIRSTNAME_LABEL = "First name";
	private static final String LASTNAME_LABEL = "Last name";
	private static final String PASSWORD_MUST_MATCH_MESSAGE = "Must enter valid confirm password";

	private JFrame parrent;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JButton registerButton;
	private JTextField firstnameField;
	private JTextField lastnameField;

	public RegistrationDialog(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;

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

		FocusListener focusListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

				if (e.getSource() instanceof JTextField) {

					JTextField jTextField = (JTextField) e.getSource();
					if (jTextField.getText().trim().equals(""))
						jTextField.setBackground(Color.RED);
					else
						jTextField.setBackground(Color.WHITE);

				}
				if (e.getSource() instanceof JPasswordField) {

					JPasswordField jPasswordField = (JPasswordField) e.getSource();
					jPasswordField.setBackground(Color.WHITE);

					if (new String(jPasswordField.getPassword()).trim().equals(""))
						jPasswordField.setBackground(Color.RED);
					else
						jPasswordField.setBackground(Color.WHITE);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

				if (e.getSource() instanceof JTextField) {

					JTextField jTextField = (JTextField) e.getSource();
					jTextField.setBackground(Color.WHITE);
				}
				if (e.getSource() instanceof JPasswordField) {

					JPasswordField jPasswordField = (JPasswordField) e.getSource();
					jPasswordField.setBackground(Color.WHITE);
				}
			}
		};

		usernameField = new JTextField(30);
		passwordField = new JPasswordField(30);
		confirmPasswordField = new JPasswordField(30);
		registerButton = new JButton(REGISTER_BUTTON_LABEL);
		registerButton.addActionListener(e -> register());
		firstnameField = new JTextField(30);
		lastnameField = new JTextField(30);

		usernameField.addFocusListener(focusListener);
		passwordField.addFocusListener(focusListener);
		confirmPasswordField.addFocusListener(focusListener);
		firstnameField.addFocusListener(focusListener);
		lastnameField.addFocusListener(focusListener);

		JLabel usernameLabel = new JLabel(USERNAME_LABEL + ":");
		JLabel passwordLabel = new JLabel(PASSWORD_LABEL + ":");
		JLabel confirmPasswordLabel = new JLabel(CONFIRM_PASSWORD_LABEL + ":");
		JLabel firstnameLabel = new JLabel(FIRSTNAME_LABEL + ":");
		JLabel lastnameLabel = new JLabel(LASTNAME_LABEL + ":");

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

		gbs.gridx = 0;
		gbs.gridy = 3;
		gbs.gridwidth = 1;
		panel.add(firstnameLabel, gbs);

		gbs.gridx = 1;
		gbs.gridy = 3;
		gbs.gridwidth = 2;
		panel.add(firstnameField, gbs);

		gbs.gridx = 0;
		gbs.gridy = 4;
		gbs.gridwidth = 1;
		panel.add(lastnameLabel, gbs);

		gbs.gridx = 1;
		gbs.gridy = 4;
		gbs.gridwidth = 2;
		panel.add(lastnameField, gbs);

		gbs.gridx = 1;
		gbs.gridy = 5;
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
		String firstname = firstnameField.getText();
		String lastname = lastnameField.getText();

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setRole_id(2);

		if (validateInput()) {

			// registration logic
			UserService userService = new UserService("rman", "rman", "localhost", 1521, "testdb");
			userService.register(user);
			parrent.dispose();
		}
	}

	/**
	 * Validates input field values.
	 * 
	 * @return
	 */
	private boolean validateInput() {

		boolean result = true;

		usernameField.setBackground(Color.WHITE);
		passwordField.setBackground(Color.WHITE);
		confirmPasswordField.setBackground(Color.WHITE);
		firstnameField.setBackground(Color.WHITE);
		lastnameField.setBackground(Color.WHITE);

		if (usernameField.getText().trim().equals("")) {
			usernameField.setBackground(Color.RED);
			result = false;
		}

		if (new String(passwordField.getPassword()).trim().equals("")) {
			passwordField.setBackground(Color.RED);
			result = false;
		}

		if (new String(confirmPasswordField.getPassword()).trim().equals("")) {
			confirmPasswordField.setBackground(Color.RED);
			result = false;
		}

		if (firstnameField.getText().trim().equals("")) {
			firstnameField.setBackground(Color.RED);
			result = false;
		}

		if (lastnameField.getText().trim().equals("")) {
			lastnameField.setBackground(Color.RED);
			result = false;
		}

		if (!new String(passwordField.getPassword()).trim().equals(new String(confirmPasswordField.getPassword()).trim())) {
			MessageBox messageBox = new MessageBox(new JFrame(""), PASSWORD_MUST_MATCH_MESSAGE);
			messageBox.setVisible(true);
			result = false;
		}

		return result;
	}

}
