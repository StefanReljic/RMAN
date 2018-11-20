package interfaces;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import model.Row;

public interface ServiceInterface {

	public void addObject(Row object);

	public void deleteObject(Row object);

	public void updateObject(Row object);

	public List<Row> readObjects(String name);

	public Connection getConnection();

	public File getInformationResourceDescription();

	public void setInformationResourceDescription();
}
