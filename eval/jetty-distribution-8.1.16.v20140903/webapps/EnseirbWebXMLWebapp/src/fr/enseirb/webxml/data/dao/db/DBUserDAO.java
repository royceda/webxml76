package fr.enseirb.webxml.data.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.enseirb.webxml.data.dao.DBManager;
import fr.enseirb.webxml.data.dao.IUserDAO;
import fr.enseirb.webxml.data.model.User;

public class DBUserDAO implements IUserDAO {
	private static final Logger LOGGER = Logger.getLogger(DBUserDAO.class.getName());

	public static final String TABLE_NAME = "USERS";

	private static final String NAME_FIELD_NAME = "NAME";

	private static final String NAME_FIELD_TYPE = "TEXT";

	public static final String CREATE_TABLE_STATEMENT = NAME_FIELD_NAME + " " + NAME_FIELD_TYPE;

	private PreparedStatement GET_ALL_USER_PSTATEMENT = null;
	private PreparedStatement CHECK_USER_EXISTENCE_PSTATEMENT = null;
	private PreparedStatement INSERT_USER_PSTATEMENT = null;
	private PreparedStatement DELETE_ALL_USERS_PSTATEMENT = null;

	public DBUserDAO() {
	}

	public List<User> getUsers() {
		if (GET_ALL_USER_PSTATEMENT == null) {
			GET_ALL_USER_PSTATEMENT = DBManager.INSTANCE.prepareStatement("SELECT * from " + TABLE_NAME + " order by "
					+ NAME_FIELD_NAME);
		}
		ResultSet resultSet;
		try {
			resultSet = GET_ALL_USER_PSTATEMENT.executeQuery();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while getting all users", e);
			resultSet = null;
		}

		List<User> result;
		if (resultSet == null) {
			result = null;
		} else {
			result = new ArrayList<User>();
			try {
				while (resultSet.next()) {
					User user = new User(resultSet.getString(NAME_FIELD_NAME));
					result.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = null;
			}
		}

		return result;
	}

	public boolean addUser(User user) {
		ResultSet resultSet;
		try {
			if (CHECK_USER_EXISTENCE_PSTATEMENT == null) {
				CHECK_USER_EXISTENCE_PSTATEMENT = DBManager.INSTANCE.prepareStatement("SELECT " + NAME_FIELD_NAME
						+ " from " + TABLE_NAME + " where " + NAME_FIELD_NAME + "=?");
			} else {
				CHECK_USER_EXISTENCE_PSTATEMENT.clearParameters();
			}

			CHECK_USER_EXISTENCE_PSTATEMENT.setString(1, user.getName());
			resultSet = CHECK_USER_EXISTENCE_PSTATEMENT.executeQuery();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while checking user existence for: "
					+ ((user == null || user.getName() == null) ? "null" : user.getName()), e);

			return false;
		}
		// ResultSet resultSet = DBManager.INSTANCE.executeQuery("SELECT " + NAME_FIELD_NAME + " from " + TABLE_NAME
		// + " where " + NAME_FIELD_NAME + "='" + user.getName() + "'");

		boolean result;
		try {
			if (resultSet != null && resultSet.next()) {
				result = false;
			} else {
				if (INSERT_USER_PSTATEMENT == null) {
					INSERT_USER_PSTATEMENT = DBManager.INSTANCE.prepareStatement("INSERT INTO " + TABLE_NAME + " ("
							+ NAME_FIELD_NAME + ") values (?)");
				} else {
					INSERT_USER_PSTATEMENT.clearParameters();
				}
				// result = DBManager.INSTANCE.executeUpdate("INSERT INTO " + TABLE_NAME + " (" + NAME_FIELD_NAME
				// + ") values ('" + user.getName() + "')");
				INSERT_USER_PSTATEMENT.setString(1, user.getName());
				int rowCount = INSERT_USER_PSTATEMENT.executeUpdate();
				result = rowCount >= 1;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while inserting user: "
					+ ((user == null || user.getName() == null) ? "null" : user.getName()), e);
			result = false;
		}
		return result;
	}

	public boolean deleteAllUSers() {
		if (DELETE_ALL_USERS_PSTATEMENT == null) {
			DELETE_ALL_USERS_PSTATEMENT = DBManager.INSTANCE.prepareStatement("DELETE FROM " + TABLE_NAME);
		}
		boolean result;
		try {
			result = DELETE_ALL_USERS_PSTATEMENT.executeUpdate() >= 1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while deleting all users", e);
			result = false;
		}
		return result;
	}
}
