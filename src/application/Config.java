package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import components.MessageBox;

public class Config {

	private static Properties defaultConfig = new Properties();
	private static final String ERROR_READING_PARAMS = "Error reading root information resource params";

	static {
		try {
			FileInputStream fis = new FileInputStream("rootDb.properties");
			defaultConfig.load(fis);
			fis.close();
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(new JFrame(), ERROR_READING_PARAMS);
			messageBox.setVisible(true);
		}
	}

	public static String getProperty(String key) {
		return defaultConfig.getProperty(key);
	}
}