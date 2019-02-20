package interfaces;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import meta.model.MetaDescription;
import model.Row;

public interface ServiceInterface {

	public boolean validateAutomaticResourceViewInputFields(String user, String password, String host, Integer port, String service);

	public void addObject(Row object) throws Exception;

	public void deleteObject(Row object, List<String> columns);

	public void updateObject(Row object);

	public List<Row> readObjects(String name, List<String> columns, Map<String, Object> conditions);

	public Connection getConnection();

	public MetaDescription getInformationResourceDescription();

	public MetaDescription readInformationResourceDescription();

}