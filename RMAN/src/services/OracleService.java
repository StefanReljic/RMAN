package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.Item;
import model.Row;

public class OracleService extends AbstractService {

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
	public void deleteObject(Row object) {

		List<String> columns = object.getItems().keySet().stream().collect(Collectors.toList());
		String sql = "delete from " + object.getTableName() + " where ";

		for (String column : columns)
			if (column.startsWith("id#"))
				sql += column.split("#")[1] + " = ? and ";

		sql = sql.substring(0, sql.length() - 4);

		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				statement.setString(1, object.getTableName());
				for (int i = 0; i < columns.size(); ++i)
					statement.setObject(i + 2, object.getItems().get(columns.get(i)));
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
	public List<Row> readObjects(String name, List<String> columns, HashMap<String, Object> conditions) {

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

							Item item = new Item(resultSetMetaData.getColumnClassName(i + 1), rs.getObject(i + 1));
							row.getItems().put(columns.get(i), item);
						}
						rows.add(row);
					}

					return rows;
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

}
