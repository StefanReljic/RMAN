package interfaces;

import java.io.File;
import java.sql.Connection;

import model.Item;

public interface ServiceInterface {

	public void addObject(Item object);

	public void deleteObject(Item object);

	public void updateObject(Item object);

	public Item readObject();

	public Connection getConnection();

	public File getInformationResourceDescription();

	public void setInformationResourceDescription();
}
