package interfaces;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import meta.model.MetaDescription;
import model.Row;

public interface ServiceInterface {

	public void addObject(Row object) throws Exception;

	public void deleteObject(Row object);

	public void updateObject(Row object);

	public List<Row> readObjects(String name, List<String> columns, HashMap<String, Object> conditions);

	public Connection getConnection();

	public MetaDescription getInformationResourceDescription();

	public void setInformationResourceDescription();

}
