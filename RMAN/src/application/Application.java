package application;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.CardLayout;

import views.LoginDialog;

public class Application {

	private static final String RMAN = "RMAN";
	private static final String LOGIN = "Login";

	private JFrame frmRman;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmRman.setVisible(false);

					JFrame frame = new JFrame(LOGIN);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					LoginDialog dialog = new LoginDialog(frame);
					dialog.setVisible(true);

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

		frmRman = new JFrame();
		frmRman.setTitle(RMAN);
		frmRman.setBounds(100, 100, 366, 231);
		frmRman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRman.getContentPane().setLayout(new CardLayout(0, 0));

	}
}
