package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserService extends OracleService {

	public static final String SQL_AUTHENTICATE = "select 1 from \"RMAN\".\"USER\" where username = ? and password = ?";
	public static final String SQL_REGISTER = "insert into \"RMAN\".\"USER\" values ( USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

	public UserService(String user, String password, String host, Integer port, String serviceId) {
		super(user, password, host, port, serviceId);
	}

	/**
	 * Used for user authentication.
	 * 
	 * @param user
	 * @return
	 */
	public boolean authenticate(User user) {

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_AUTHENTICATE)) {

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getPassword());
				ResultSet rs = preparedStatement.executeQuery();
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Used for user registration.
	 * 
	 * @param user
	 */
	public void register(User user) {

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REGISTER)) {

				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setString(3, user.getFirstname());
				preparedStatement.setString(4, user.getLastname());
				preparedStatement.setLong(5, user.getRole_id());
				preparedStatement.execute();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
