package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import application.Config;
import components.MessageBox;
import interfaces.ServiceInterface;
import model.Item;
import model.Row;
import services.OracleService;

/**
 * View for user registration.
 */
public class RegistrationView extends JDialog {

	private static final long serialVersionUID = -3891948594599857016L;

	private static final String USERNAME_LABEL = "Username";
	private static final String PASSWORD_LABEL = "Password";
	private static final String CONFIRM_PASSWORD_LABEL = "Confirm password";
	private static final String REGISTER_BUTTON_LABEL = "Register";
	private static final String REGISTRATION_LABEL = "Registration";
	private static final String FIRSTNAME_LABEL = "First name";
	private static final String LASTNAME_LABEL = "Last name";
	private static final String PASSWORD_MUST_MATCH_MESSAGE = "Must enter valid confirm password";

	private JFrame parrent;
	private LoginView loginView;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JButton registerButton;
	private JTextField firstnameField;
	private JTextField lastnameField;

	public RegistrationView(JFrame parrent, LoginView loginView) {
		super(parrent);

		this.parrent = parrent;
		this.loginView = loginView;

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
				loginView.setVisible(true);
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
		gbs.anchor = GridBagConstraints.WEST;

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

		Row row = new Row();
		Map<String, Item> items = new LinkedHashMap<String, Item>();

		ServiceInterface serviceInterface = new OracleService(Config.getProperty("user"), Config.getProperty("password"), Config.getProperty("host"),
				Integer.parseInt(Config.getProperty("port")), Config.getProperty("resourceId"));

		List<String> idColumns = new LinkedList<String>();
		idColumns.add("USER_SEQ.NEXTVAL");
		List<Row> rows = serviceInterface.readObjects("dual", idColumns, null);
		BigDecimal nextId = (BigDecimal) rows.get(0).getItems().get("USER_SEQ.NEXTVAL").getValue();

		Item idItem = new Item("String", nextId);
		Item usernameItem = new Item("String", username);
		Item passwordItem = new Item("String", password);
		Item firstnameItem = new Item("String", firstname);
		Item lastnameItem = new Item("String", lastname);
		Item roleItem = new Item("long", 2);

		items.put("id", idItem);
		items.put("username", usernameItem);
		items.put("password", passwordItem);
		items.put("first_name", firstnameItem);
		items.put("last_name", lastnameItem);
		items.put("role_id", roleItem);

		row.setTableName("user_table");
		row.setItems(items);

		if (validateInput()) {

			// registration logic
			try {
				serviceInterface.addObject(row);
			} catch (Exception e) {
				e.printStackTrace();
			}
			parrent.dispose();
			loginView.setVisible(true);
		}
	}

	/**
	 * Validates input field values.
	 * 
	 * @return
	 */
	private boolean validateInput() {

		boolean result = true;

		usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		firstnameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lastnameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		if (usernameField.getText().trim().equals("")) {
			usernameField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		if (new String(passwordField.getPassword()).trim().equals("")) {
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		if (new String(confirmPasswordField.getPassword()).trim().equals("")) {
			confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		if (firstnameField.getText().trim().equals("")) {
			firstnameField.setBorder(BorderFactory.createLineBorder(Color.RED));
			result = false;
		}

		if (lastnameField.getText().trim().equals("")) {
			lastnameField.setBorder(BorderFactory.createLineBorder(Color.RED));
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
