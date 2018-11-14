package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import model.BasicGrid;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import java.awt.Dimension;

public class Application {

	private JFrame frmRman;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	/**
	 * @wbp.nonvisual location=128,329
	 */
	private final JDialog dialog = new JDialog();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmRman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JLabel usernameLabel = new JLabel("Username:");
		dialog.getContentPane().add(usernameLabel, BorderLayout.NORTH);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Password:");
		dialog.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		usernameTextField = new JTextField();
		dialog.getContentPane().add(usernameTextField, BorderLayout.NORTH);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		dialog.getContentPane().add(passwordTextField, BorderLayout.NORTH);
		
		JButton loginButton = new JButton("Login");
		dialog.getContentPane().add(loginButton, BorderLayout.NORTH);
		
		JLabel lblLogin = new JLabel("Login");
		dialog.getContentPane().add(lblLogin, BorderLayout.NORTH);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = usernameTextField.getText();
				String password = new String(passwordTextField.getPassword());
				
				if(username == null || password == null) {
					return;
				}
				if(username.trim().equals("") || password.trim().equals("")) {
					return;
				}
				
				
				
			}
		});
		
		
		frmRman = new JFrame();
		frmRman.setTitle("RMAN");
		frmRman.setBounds(100, 100, 366, 231);
		frmRman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRman.getContentPane().setLayout(new CardLayout(0, 0));
	
		BasicGrid basicGrid = new BasicGrid();
		
		JPanel panel = new JPanel();
		frmRman.getContentPane().add(panel, "name_1039595909987894");
		panel.setLayout(null);
		panel.add(basicGrid);
		
		dialog.setAlwaysOnTop(false);
		dialog.setTitle("Login\r\n");
		dialog.setVisible(true);
		frmRman.setVisible(false);
	}
}
