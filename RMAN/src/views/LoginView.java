package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import interfaces.ServiceInterface;
import model.Row;
import services.OracleService;

public class LoginView extends Dialog {

	private static final long serialVersionUID = -390305771543873580L;

	private static final String USERNAME_LABEL = "Username";
	private static final String PASSWORD_LABEL = "Password";
	private static final String LOGIN_LABEL = "Login";
	private static final String REGISTRATION_LABEL = "Registration";
	private static final String MAIN_LABEL = "Main";

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JButton registerButton;

	private RegistrationView registrationDialog;
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

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				login();
			}
		}
	};

	public LoginView(JFrame parrent) {
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
				if (registrationDialog != null && registrationDialog.isVisible())
					e.getWindow().setVisible(false);
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
				e.getWindow().setVisible(true);
			}
		});

		FocusListener focusListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

				if (e.getSource() instanceof JTextField) {

					JTextField jTextField = (JTextField) e.getSource();
					if (jTextField.getText().trim().equals(""))
						jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
					else
						jTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

				}
				if (e.getSource() instanceof JPasswordField) {

					JPasswordField jPasswordField = (JPasswordField) e.getSource();
					jPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					if (new String(jPasswordField.getPassword()).trim().equals(""))
						jPasswordField.setBorder(BorderFactory.createLineBorder(Color.RED));
					else
						jPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

				if (e.getSource() instanceof JTextField) {

					JTextField jTextField = (JTextField) e.getSource();
					jTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
				if (e.getSource() instanceof JPasswordField) {

					JPasswordField jPasswordField = (JPasswordField) e.getSource();
					jPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		};

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbs = new GridBagConstraints();
		gbs.anchor = GridBagConstraints.WEST;
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

		loginButton = new JButton(LOGIN_LABEL);
		gbs.gridx = 1;
		gbs.gridy = 3;
		gbs.gridwidth = 1;
		panel.add(loginButton, gbs);

		registerButton = new JButton(REGISTRATION_LABEL);
		gbs.gridx = 3;
		gbs.gridy = 3;
		gbs.gridwidth = 1;
		panel.add(registerButton, gbs);

		panel.setBounds(0, 0, 400, 200);
		setBounds(0, 0, 400, 200);
		add(panel, BorderLayout.CENTER);

		loginButton.addActionListener(e -> login());
		loginButton.addKeyListener(keyListener);
		usernameTextField.addKeyListener(keyListener);
		passwordTextField.addKeyListener(keyListener);
		registerButton.addActionListener(e -> register());
		usernameTextField.addFocusListener(focusListener);
		passwordTextField.addFocusListener(focusListener);

		this.parrent.getRootPane().setDefaultButton(loginButton);

		setTitle(LOGIN_LABEL);
		pack();
		setResizable(false);
		setLocationRelativeTo(this.parrent);
	}

	public void register() {

		registrationDialog = new RegistrationView(new JFrame(REGISTRATION_LABEL), this);
		registrationDialog.setVisible(true);
	}

	public void login() {

		if (!validateInput())
			return;

		String username = usernameTextField.getText();
		String password = new String(passwordTextField.getPassword());

		ServiceInterface oracleService = new OracleService("rman", "rman", "localhost", 1521, "testdb");

		HashMap<String, Object> objects = new HashMap<String, Object>();
		objects.put("username", username);
		objects.put("password", password);
		List<Row> result = oracleService.readObjects("user_table", null, objects);

		if (result != null && result.size() != 0) {
			MainView main = new MainView(new JFrame(MAIN_LABEL));
			main.setVisible(true);
			dispose();
		}
		parrent.setVisible(true);
		return;
	}

	public boolean validateInput() {

		boolean result = true;

		usernameTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		passwordTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		if (usernameLabel.getText().trim().equals("")) {
			usernameTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		if (new String(passwordTextField.getPassword()).trim().equals("")) {
			passwordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		return result;
	}

	public String getUsername() {
		return usernameTextField.getText();
	}

}
