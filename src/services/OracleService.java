package services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

<<<<<<< HEAD:RMAN/src/services/OracleService.java
import javax.swing.JFrame;

import org.apache.poi.util.IOUtils;
=======
import org.apache.commons.io.IOUtils;
>>>>>>> 78340d376cff8fb635393e55868dec34a1bf4fe2:src/services/OracleService.java

import application.Config;
import components.MessageBox;
import interfaces.ServiceInterface;
import meta.model.MetaDescription;
import meta.model.MetaEntity;
import meta.model.MetaInfo;
import meta.model.MetaProperty;
import meta.model.MetaRelation;
import meta.model.MetaRow;
import model.Item;
import model.Row;

public class OracleService extends AbstractService {

	private static final String SQL_READ_COLUMNS = "select table_name, column_name, data_type from user_tab_cols";
	private static final String SQL_READ_RELATIONS = "select prim.table_name, prim.column_name, ucc.table_name, ucc.column_name from user_constraints uc "
			+ "join user_cons_columns ucc " + "    on uc.constraint_name = ucc.constraint_name " + "join "
			+ "    (select ucp.constraint_name ,uccp.table_name, uccp.column_name, uccp.position from user_constraints ucp join user_cons_columns uccp "
			+ "            on ucp.constraint_name = uccp.constraint_name) prim "
			+ "    on prim.constraint_name = uc.r_constraint_name and prim.position = ucc.position " + "where uc.constraint_type = 'R' "
			+ "order by ucc.table_name ";

	private static final String SQL_READ_PRIMARY_KEYS = "select ucc.table_name, ucc.column_name from user_cons_columns ucc "
			+ "join user_constraints uc on ucc.constraint_name = uc.constraint_name where constraint_type='P' order by table_name";

	public static final String CANNOT_ADD_INFORMATION_RESOURCE_MESSAGE = "Cannot add information resource";
	private String user;
	private String password;
	private String host;
	private Integer port;
	private String serviceId;

	public OracleService() {
		super();
	}

	public OracleService(String user, String password, String host, Integer port, String serviceId) {
		super();
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.serviceId = serviceId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public boolean validateAutomaticResourceViewInputFields(String user, String password, String host, Integer port, String service) {

		boolean result = true;

		if (user == null || user.equals(""))
			result = false;

		if (password == null || password.equals(""))
			result = false;

		if (host == null || host.equals(""))
			result = false;

		if (port == null)
			result = false;

		if (service == null || service.equals(""))
			result = false;

		return result;
	}

	@Override
	public void addObject(Row object) throws SQLException {

		List<String> keys = object.getItems().keySet().stream().collect(Collectors.toList());
		String sql = "insert into " + object.getTableName() + " (";

		for (String key : keys)
			sql += key + ",";

		sql = sql.substring(0, sql.length() - 1);
		sql += ") values (";
		List<Item> items = object.getItems().values().stream().collect(Collectors.toList());
		sql += IntStream.range(0, object.getItems().size()).mapToObj(i -> "?,").collect(Collectors.joining());

		sql = sql.substring(0, sql.length() - 1);
		sql += ")";

		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				for (int i = 0; i < items.size(); ++i) {
					statement.setObject(i + 1, items.get(i).getValue());
				}
				try (ResultSet rs = statement.executeQuery()) {

				}
			}
		}
	}

	@Override
	public void deleteObject(Row object, List<String> columns) {

		String sql = "delete from " + object.getTableName() + " where ";

		for (String column : columns)
			sql += column + " = ? and ";

		sql = sql.substring(0, sql.length() - 4);

		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				for (int i = 0; i < columns.size(); ++i)
					statement.setObject(i + 1, object.getItems().get(columns.get(i)).getValue());
				try (ResultSet rs = statement.executeQuery()) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateObject(Row object) {

		List<String> columns = object.getItems().keySet().stream().collect(Collectors.toList());
		String sql = "update ? ";
		for (String key : columns) {
			sql += "set " + key + " = ?, ";
		}
		sql = sql.substring(0, sql.length() - 2);
		sql += "where ";

		for (String column : columns)
			if (column.startsWith("id#")) {
				sql += column.split("#")[1] + " = ? and ";
			}
		sql = sql.substring(0, sql.length() - 4);

		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				statement.setString(1, object.getTableName());
				for (int i = 0; i < columns.size(); ++i) {
					statement.setObject(i + 2, object.getItems().get(columns.get(i)));
				}

				for (int i = 0; i < columns.size(); ++i)
					if (columns.get(i).startsWith("id#")) {
						statement.setObject(i + columns.size() + 2, columns.get(i).split("#")[1]);
					}
				statement.execute();
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Row> readObjects(String name, List<String> columns, Map<String, Object> conditions) {

		String columnString = "";
		if (columns == null || columns.size() == 0)
			return null;
		else {
			for (String col : columns) {
				columnString += col + ",";
			}
			columnString = columnString.substring(0, columnString.length() - 1);
		}

		String sql = "select " + columnString + " from " + name;

		List<String> keys = null;
		if (conditions != null && conditions.size() != 0) {
			sql += " where ";
			keys = conditions.keySet().stream().collect(Collectors.toList());
			for (String key : keys) {
				sql += key + " = ? and ";
			}
			sql = sql.substring(0, sql.length() - 4);
		}
		List<Row> rows = new LinkedList<Row>();

		try (Connection connection = getConnection();) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				if (conditions != null) {
					for (int i = 0; i < keys.size(); ++i) {
						statement.setObject(i + 1, conditions.get(keys.get(i)));
					}
				}

				try (ResultSet rs = statement.executeQuery()) {

					ResultSetMetaData resultSetMetaData = rs.getMetaData();
					int numberOfArguments = resultSetMetaData.getColumnCount();

					while (rs.next()) {

						Row row = new Row();
						row.setTableName(name);
						for (int i = 0; i < numberOfArguments; ++i) {

							Item item = null;
							if (resultSetMetaData.getColumnClassName(i + 1).toLowerCase().contains("blob")) {
								item = new Item("byte[]", IOUtils.toByteArray(rs.getBinaryStream(i + 1)));
							} else
								item = new Item(resultSetMetaData.getColumnClassName(i + 1), rs.getObject(i + 1));
							row.getItems().put(columns.get(i), item);
						}
						rows.add(row);
					}

					return rows;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Connection getConnection() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + serviceId, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@Override
	public MetaDescription getInformationResourceDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MetaDescription readInformationResourceDescription() {

		String oldTableName = "";
		MetaDescription metaDescription = new MetaDescription();
		MetaInfo metaInfo = new MetaInfo(user, password, host, port, serviceId);

		List<MetaEntity> metaEntities = new LinkedList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(SQL_READ_COLUMNS)) {
				try (ResultSet rs = statement.executeQuery()) {

					MetaEntity metaEntity = null;
					MetaRow metaRow = null;

					while (rs.next()) {

						String tableName = rs.getString(1);
						String columnName = rs.getString(2);
						String columnType = rs.getString(3);

						if (oldTableName.equals("")) {

							oldTableName = tableName;
							metaEntity = new MetaEntity();
							metaRow = new MetaRow();

							MetaProperty metaProperty = new MetaProperty();
							metaProperty.setType(columnType);

							metaRow.getItems().put(columnName, metaProperty);
							metaEntity.setEntityName(tableName);
							metaEntity.setMetaRow(metaRow);

						} else if (oldTableName.equals(tableName)) {
							MetaProperty metaProperty = new MetaProperty();
							metaProperty.setType(columnType);
							metaEntity.getMetaRow().getItems().put(columnName, metaProperty);

						} else {
							metaEntities.add(metaEntity);
							metaEntity = new MetaEntity();
							metaRow = new MetaRow();
							oldTableName = tableName;

							MetaProperty metaProperty = new MetaProperty();
							metaProperty.setType(columnType);

							metaRow.getItems().put(columnName, metaProperty);
							metaEntity.setEntityName(tableName);
							metaEntity.setMetaRow(metaRow);
						}

					}

					metaEntities.add(metaEntity);
				}
			}
			try (PreparedStatement statement = connection.prepareStatement(SQL_READ_RELATIONS)) {
				try (ResultSet rs = statement.executeQuery()) {

					String oldParrent = "";
					String oldChild = "";
					MetaEntity metaEntity = null;
					MetaRelation metaRelation = null;

					while (rs.next()) {

						String parrentTable = rs.getString(1);
						String parrentId = rs.getString(2);
						String childTable = rs.getString(3);
						String childId = rs.getString(4);

						if (oldParrent.equals("") && oldChild.equals("")) {

							oldParrent = parrentTable;
							oldChild = childTable;

							for (MetaEntity entity : metaEntities)
								if (entity.getEntityName().equals(parrentTable)) {
									metaEntity = entity;
									break;
								}

							metaRelation = new MetaRelation();
							metaRelation.setParrentTable(parrentTable);
							metaRelation.getParrentIds().put(parrentId, new MetaProperty());
							metaRelation.setChildTable(childTable);
							metaRelation.getChildIds().put(childId, new MetaProperty());

							metaEntity.getRelations().add(metaRelation);

						} else if (oldParrent.equals(parrentTable) && oldChild.equals(childTable)) {

							metaRelation.getParrentIds().put(parrentId, new MetaProperty());
							metaRelation.getChildIds().put(childId, new MetaProperty());

							metaEntity.getRelations().add(metaRelation);

						} else {

							oldParrent = parrentTable;
							oldChild = childTable;

							for (MetaEntity entity : metaEntities)
								if (entity.getEntityName().equals(parrentTable)) {
									metaEntity = entity;
									break;
								}

							metaRelation = new MetaRelation();
							metaRelation.setParrentTable(parrentTable);
							metaRelation.getParrentIds().put(parrentId, new MetaProperty());
							metaRelation.setChildTable(childTable);
							metaRelation.getChildIds().put(childId, new MetaProperty());

							metaEntity.getRelations().add(metaRelation);
						}

					}
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(SQL_READ_PRIMARY_KEYS)) {
				try (ResultSet rs = statement.executeQuery()) {

					while (rs.next()) {

						String tableName = rs.getString(1);
						String columnName = rs.getString(2);

						for (MetaEntity entity : metaEntities)
							if (entity.getEntityName().equals(tableName)) {
								entity.getMetaIds().put(columnName, new MetaProperty());
							}
					}
				}
			}

			metaDescription.setMetaInfo(metaInfo);
			metaDescription.setMetaEntities(metaEntities);

			return metaDescription;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void addInformationResource(MetaDescription metaDescription) {

		if (metaDescription != null) {

			byte[] metaDescriptionBytes = null;
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bos);

				out.writeObject(metaDescription);
				out.close();
				metaDescriptionBytes = bos.toByteArray();
				bos.close();
			} catch (IOException ex) {
				MessageBox messageBox = new MessageBox(new JFrame(), ex.getMessage());
				messageBox.setVisible(true);
			}

			ServiceInterface serviceInterface = new OracleService(Config.getProperty("user"), Config.getProperty("password"),
					Config.getProperty("host"), Integer.parseInt(Config.getProperty("port")), Config.getProperty("resourceId"));
			List<String> idColumns = new LinkedList<String>();
			idColumns.add("INFORMATION_RESOURCE_SEQ.NEXTVAL");
			List<Row> rows = serviceInterface.readObjects("dual", idColumns, null);
			BigDecimal nextId = (BigDecimal) rows.get(0).getItems().get("INFORMATION_RESOURCE_SEQ.NEXTVAL").getValue();

			Row row = new Row();
			row.setTableName("INFORMATION_RESOURCE");
			Map<String, Item> items = new LinkedHashMap<String, Item>();

			Item idItem = new Item("BigDecimal", nextId);
			items.put("ID", idItem);

			Item userItem = new Item("String", metaDescription.getMetaInfo().getUser());
			items.put("CONN_USER", userItem);

			Item passwordItem = new Item("String", metaDescription.getMetaInfo().getPassword());
			items.put("CONN_PASSWORD", passwordItem);

			Item hostItem = new Item("String", metaDescription.getMetaInfo().getHost());
			items.put("HOST", hostItem);

			Item portItem = new Item("int", metaDescription.getMetaInfo().getPort());
			items.put("PORT", portItem);

			Item resourceIdItem = new Item("String", metaDescription.getMetaInfo().getResourceId());
			items.put("NAME", resourceIdItem);

			Item metaDescriptionBytesItem = new Item("byte[]", metaDescriptionBytes);
			items.put("META_DESCRIPTION", metaDescriptionBytesItem);

			row.setItems(items);

			Item uir_ir = new Item("BigDecimal", nextId);
			Item uir_user = new Item("BigDecimal", 1);
			Row uirRow = new Row();
			Map<String, Item> uirItems = new LinkedHashMap<String, Item>();
			uirItems.put("INFORMATION_RESOURCE_ID", uir_ir);
			uirItems.put("USER_ID", uir_user);
			uirRow.setItems(uirItems);
			uirRow.setTableName("U_IR");

			try {
				serviceInterface.addObject(row);
				serviceInterface.addObject(uirRow);
			} catch (SQLException e1) {
				MessageBox messageBox = new MessageBox(new JFrame(), CANNOT_ADD_INFORMATION_RESOURCE_MESSAGE);
				messageBox.setVisible(true);
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}