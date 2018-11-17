package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class LoginDialog extends Dialog {

	private static final String USERNAME_LABEL = "Username";
	private static final String PASSWORD_LABEL = "Password";
	private static final String LOGIN_LABEL = "Login";

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton loginButton;

	private JFrame parrent;
	private KeyListener keyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			login();
		}
	};

	public LoginDialog(JFrame parrent) {
		super(parrent);

		this.parrent = parrent;
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();

		gbs.fill = GridBagConstraints.HORIZONTAL;

		usernameLabel = new JLabel(USERNAME_LABEL + ":");
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		panel.add(usernameLabel, gbs);

		usernameTextField = new JTextField(30);
		gbs.gridx = 1;
		gbs.gridy = 0;
		gbs.gridwidth = 2;
		panel.add(usernameTextField, gbs);

		passwordLabel = new JLabel(PASSWORD_LABEL + ":");
		gbs.gridx = 0;
		gbs.gridy = 1;
		gbs.gridwidth = 1;
		panel.add(passwordLabel, gbs);

		passwordTextField = new JPasswordField(30);
		gbs.gridx = 1;
		gbs.gridy = 1;
		gbs.gridwidth = 2;
		panel.add(passwordTextField, gbs);
		panel.setBorder(new LineBorder(Color.GRAY));

		loginButton = new JButton("Login");
		loginButton.addActionListener(e -> login());
		loginButton.addKeyListener(keyListener);
		usernameTextField.addKeyListener(keyListener);
		passwordTextField.addKeyListener(keyListener);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		buttonPanel.add(loginButton, gbs);

		JPanel finalPanel = new JPanel();

		gbs.gridx = 0;
		gbs.gridy = 0;
		gbs.gridwidth = 1;
		finalPanel.add(panel, gbs);

		gbs.gridx = 0;
		gbs.gridy = 1;
		gbs.gridwidth = 1;
		finalPanel.add(loginButton, gbs);

		finalPanel.setBounds(0, 0, 400, 200);
		setBounds(0, 0, 400, 200);
		add(finalPanel, BorderLayout.CENTER);

		this.parrent.getRootPane().setDefaultButton(loginButton);

		setTitle(LOGIN_LABEL);
		pack();
		setResizable(false);
		setLocationRelativeTo(this.parrent);
	}

	public void login() {

		String username = usernameTextField.getText();
		String password = new String(passwordTextField.getPassword());

		if (username == null || password == null) {
			return;
		}
		if (username.trim().equals("") || password.trim().equals("")) {
			return;
		}

		// add login logic

		dispose();
		parrent.setVisible(true);
	}

	public String getUsername() {
		return usernameTextField.getText();
	}

}
