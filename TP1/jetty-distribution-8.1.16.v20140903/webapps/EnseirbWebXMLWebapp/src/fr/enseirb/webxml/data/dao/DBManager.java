package fr.enseirb.webxml.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.enseirb.webxml.data.dao.db.DBTaskDAO;
import fr.enseirb.webxml.data.dao.db.DBUserDAO;
import fr.enseirb.webxml.data.dao.gae.GAETaskDAO;
import fr.enseirb.webxml.data.dao.gae.GAEUserDAO;
import fr.enseirb.webxml.servlet.listener.WebAppInitializer;

public class DBManager {
	private static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());

	public static DBManager INSTANCE = new DBManager();

	private boolean isGoogleAppEngine;
	private IUserDAO userDAO = null;
	private ITaskDAO taskDAO = null;

	private Connection connection;

	private DBManager() {
		String sPathToDB = WebAppInitializer.ROOT + "db/tasks.sqlite";
		String log;
		if (sPathToDB.contains("EnseirbWebXMLWebapp")) {
			// on depoye sur un serveur local
			isGoogleAppEngine = false;
			log = "SQlite";
			try {

				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + WebAppInitializer.ROOT + "/db/tasks.sqlite");
				connection.setAutoCommit(true);

				executeUpdate("CREATE TABLE IF NOT EXISTS " + DBTaskDAO.TABLE_NAME + " ("
						+ DBTaskDAO.CREATE_TABLE_STATEMENT + ")");
				executeUpdate("CREATE TABLE IF NOT EXISTS " + DBUserDAO.TABLE_NAME + " ("
						+ DBUserDAO.CREATE_TABLE_STATEMENT + ")");

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Error while getting connexion", e);
				connection = null;
			} catch (ClassNotFoundException e) {
				LOGGER.log(Level.SEVERE, "Error while getting connexion", e);
				connection = null;
			}

		} else {
			// deploiement sur Google App Engine
			isGoogleAppEngine = true;
			log = "Google App Engine";
		}
		LOGGER.log(Level.INFO, "Assuming " + log + " context");
	}

	public IUserDAO getUserDAO() {
		if (userDAO == null) {
			if (isGoogleAppEngine) {
				userDAO = new GAEUserDAO();
			} else {
				userDAO = new DBUserDAO();
			}
		}
		return userDAO;
	}

	public ITaskDAO getTaskDAO() {
		if (taskDAO == null) {
			if (isGoogleAppEngine) {
				taskDAO = new GAETaskDAO();
			} else {
				taskDAO = new DBTaskDAO();
			}
		}
		return taskDAO;
	}

	public ResultSet executeQuery(String query) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while executing query", e);
			resultSet = null;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				resultSet = null;
			}
		}
		return resultSet;
	}

	public boolean executeUpdate(String query) {
		Statement statement = null;
		int result;
		try {
			statement = connection.createStatement();
			result = statement.executeUpdate(query);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while executing update", e);
			result = -1;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = -1;
			}
		}
		return result > 0;
	}

	public PreparedStatement prepareStatement(String statement) {
		PreparedStatement pStatement;
		try {
			pStatement = connection.prepareStatement(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while preparing statement", e);
			pStatement = null;
		}
		return pStatement;
	}
}
