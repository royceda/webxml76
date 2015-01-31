package fr.enseirb.webxml.data.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.enseirb.webxml.data.dao.DBManager;
import fr.enseirb.webxml.data.dao.ITaskDAO;
import fr.enseirb.webxml.data.model.Task;

public class DBTaskDAO implements ITaskDAO {
	private static final Logger LOGGER = Logger.getLogger(DBTaskDAO.class.getName());

	public static final String TABLE_NAME = "TASKS";

	private static final String ID_FIELD_NAME = "ID";
	private static final String ASKER_FIELD_NAME = "ASKER";
	private static final String OWNER_FIELD_NAME = "OWNER";
	private static final String TITLE_FIELD_NAME = "TITLE";
	private static final String DESCRIPTION_FIELD_NAME = "DESCRIPTION";
	private static final String CREATION_DATE_FIELD_NAME = "CREATION_DATE";
	private static final String DEADLINE_FIELD_NAME = "DEADLINE";
	private static final String PRIORITY_FIELD_NAME = "PRIORITY";
	private static final String DONE_FIELD_NAME = "DONE";

	private static final String ID_FIELD_TYPE = "INTEGER";
	private static final String ASKER_FIELD_TYPE = "TEXT";
	private static final String OWNER_FIELD_TYPE = "TEXT";
	private static final String TITLE_FIELD_TYPE = "TEXT";
	private static final String DESCRIPTION_FIELD_TYPE = "TEXT";
	private static final String CREATION_DATE_FIELD_TYPE = "INTEGER"; // Date
	private static final String DEADLINE_FIELD_TYPE = "INTEGER"; // Date
	private static final String PRIORITY_FIELD_TYPE = "INTEGER";
	private static final String DONE_FIELD_TYPE = "INTEGER"; // boolean (0:false, 1:true)

	private static final int IS_TASK_NOT_DONE = 0;
	private static final int IS_TASK_DONE = 1;

	public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE + ", " + ASKER_FIELD_NAME
			+ " " + ASKER_FIELD_TYPE + ", " + OWNER_FIELD_NAME + " " + OWNER_FIELD_TYPE + ", " + TITLE_FIELD_NAME + " "
			+ TITLE_FIELD_TYPE + ", " + DESCRIPTION_FIELD_NAME + " " + DESCRIPTION_FIELD_TYPE + ", "
			+ CREATION_DATE_FIELD_NAME + " " + CREATION_DATE_FIELD_TYPE + ", " + DEADLINE_FIELD_NAME + " "
			+ DEADLINE_FIELD_TYPE + ", " + PRIORITY_FIELD_NAME + " " + PRIORITY_FIELD_TYPE + ", " + DONE_FIELD_NAME
			+ " " + DONE_FIELD_TYPE;

	private PreparedStatement GET_ALL_TASKS_PSTATEMENT = null;
	private PreparedStatement GET_USER_TASKS_PSTATEMENT = null;
	private PreparedStatement UPDATE_TASK_PSTATEMENT = null;
	private PreparedStatement INSERT_TASK_PSTATEMENT = null;
	private PreparedStatement DELETE_ALL_TASKS_PSTATEMENT = null;

	private int lastId = Task.NO_ID;

	public DBTaskDAO() {
	}

	public List<Task> getTasks() {
		return getTasksWithIDFilter(null);
	}

	public Task getTask(int id) {
		Collection<Task> tasks = getTasksWithIDFilter(id);
		Task task;
		if (tasks.size() == 1) {
			task = tasks.iterator().next();
		} else {
			task = null;
		}
		return task;
	}

	private List<Task> getTasksWithIDFilter(Integer id) {
		if (GET_ALL_TASKS_PSTATEMENT == null) {
			GET_ALL_TASKS_PSTATEMENT = DBManager.INSTANCE.prepareStatement("SELECT * from " + TABLE_NAME + " order by "
					+ ID_FIELD_NAME);
		}
		if (GET_USER_TASKS_PSTATEMENT == null) {
			GET_USER_TASKS_PSTATEMENT = DBManager.INSTANCE.prepareStatement("SELECT * from " + TABLE_NAME + " where "
					+ ID_FIELD_NAME + "=?" + " order by " + ID_FIELD_NAME);
		}

		ResultSet resultSet;
		try {
			PreparedStatement pStatement;
			if (id != null) {
				pStatement = GET_USER_TASKS_PSTATEMENT;
				pStatement.setInt(1, id);

			} else {
				pStatement = GET_ALL_TASKS_PSTATEMENT;
			}

			resultSet = pStatement.executeQuery();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while getting tasks", e);
			resultSet = null;
		}

		List<Task> result;
		if (resultSet == null) {
			result = new ArrayList<Task>();
		} else {
			result = new ArrayList<Task>();
			try {
				while (resultSet.next()) {
					Task task = new Task();
					task.setId(resultSet.getInt(ID_FIELD_NAME));
					task.setAsker(resultSet.getString(ASKER_FIELD_NAME));
					task.setOwner(resultSet.getString(OWNER_FIELD_NAME));
					task.setTitle(resultSet.getString(TITLE_FIELD_NAME));
					task.setDescription(resultSet.getString(DESCRIPTION_FIELD_NAME));
					task.setCreationDate(new Date(resultSet.getLong(CREATION_DATE_FIELD_NAME)));
					task.setDeadline(new Date(resultSet.getLong(DEADLINE_FIELD_NAME)));
					task.setPriority(resultSet.getInt(PRIORITY_FIELD_NAME));
					task.setDone(resultSet.getInt(DONE_FIELD_NAME) != IS_TASK_NOT_DONE);

					result.add(task);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = null;
			}
		}

		return result;
	}

	private int getNextTaskId() {
		if (lastId == Task.NO_ID) {
			Collection<Task> tasks = getTasks();
			for (Iterator<Task> taskIte = tasks.iterator(); taskIte.hasNext();) {
				Task task = taskIte.next();
				if (task.getId() > lastId) {
					lastId = task.getId();
				}
			}
		}
		return ++lastId;
	}

	public boolean addOrModify(Task task) {
		boolean result;

		if (task.getId() > Task.NO_ID) {
			// modification
			try {
				if (UPDATE_TASK_PSTATEMENT == null) {
					UPDATE_TASK_PSTATEMENT = DBManager.INSTANCE.prepareStatement("UPDATE " + TABLE_NAME + " SET "
							+ ASKER_FIELD_NAME + "=?, " + OWNER_FIELD_NAME + "=?, " + TITLE_FIELD_NAME + "=?, "
							+ DESCRIPTION_FIELD_NAME + "=?, " + DEADLINE_FIELD_NAME + "=?, " + PRIORITY_FIELD_NAME
							+ "=?, " + DONE_FIELD_NAME + "=?" + " where " + ID_FIELD_NAME + "=?");
				} else {
					UPDATE_TASK_PSTATEMENT.clearParameters();
				}
				UPDATE_TASK_PSTATEMENT.setString(1, task.getAsker());
				UPDATE_TASK_PSTATEMENT.setString(2, task.getOwner());
				UPDATE_TASK_PSTATEMENT.setString(3, task.getTitle());
				UPDATE_TASK_PSTATEMENT.setString(4, task.getDescription());
				UPDATE_TASK_PSTATEMENT.setLong(5, task.getDeadline().getTime());
				UPDATE_TASK_PSTATEMENT.setInt(6, task.getPriority());
				UPDATE_TASK_PSTATEMENT.setInt(7, (task.isDone() ? IS_TASK_DONE : IS_TASK_NOT_DONE));
				UPDATE_TASK_PSTATEMENT.setInt(8, task.getId());

				result = UPDATE_TASK_PSTATEMENT.executeUpdate() >= 1;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Error while updating task", e);
				result = false;
			}
			// result = DBManager.INSTANCE.executeUpdate("UPDATE " + TABLE_NAME + " SET " + ASKER_FIELD_NAME + "='"
			// + task.getAsker() + "', " + OWNER_FIELD_NAME + "='" + task.getOwner() + "', " + TITLE_FIELD_NAME
			// + "='" + task.getTitle() + "', " + DESCRIPTION_FIELD_NAME + "='" + task.getDescription() + "', "
			// + DEADLINE_FIELD_NAME + "=" + task.getDeadline().getTime() + ", " + PRIORITY_FIELD_NAME + "="
			// + task.getPriority() + ", " + DONE_FIELD_NAME + "="
			// + (task.isDone() ? IS_TASK_DONE : IS_TASK_NOT_DONE) + " where " + ID_FIELD_NAME + "="
			// + task.getId());

		} else {
			// ajout
			task.setId(getNextTaskId());

			try {
				if (INSERT_TASK_PSTATEMENT == null) {
					INSERT_TASK_PSTATEMENT = DBManager.INSTANCE.prepareStatement("INSERT INTO " + TABLE_NAME + " ("
							+ ID_FIELD_NAME + ", " + ASKER_FIELD_NAME + ", " + OWNER_FIELD_NAME + ", "
							+ TITLE_FIELD_NAME + ", " + DESCRIPTION_FIELD_NAME + ", " + CREATION_DATE_FIELD_NAME + ", "
							+ DEADLINE_FIELD_NAME + ", " + PRIORITY_FIELD_NAME + ", " + DONE_FIELD_NAME
							+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				} else {
					INSERT_TASK_PSTATEMENT.clearParameters();
				}
				INSERT_TASK_PSTATEMENT.setInt(1, task.getId());
				INSERT_TASK_PSTATEMENT.setString(2, task.getAsker());
				INSERT_TASK_PSTATEMENT.setString(3, task.getOwner());
				INSERT_TASK_PSTATEMENT.setString(4, task.getTitle());
				INSERT_TASK_PSTATEMENT.setString(5, task.getDescription());
				INSERT_TASK_PSTATEMENT.setLong(6, new Date().getTime());
				INSERT_TASK_PSTATEMENT.setLong(7, task.getDeadline().getTime());
				INSERT_TASK_PSTATEMENT.setInt(8, task.getPriority());
				INSERT_TASK_PSTATEMENT.setInt(9, (task.isDone() ? IS_TASK_DONE : IS_TASK_NOT_DONE));

				result = INSERT_TASK_PSTATEMENT.executeUpdate() >= 1;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Error while inserting task", e);
				result = false;
			}
			// result = DBManager.INSTANCE.executeUpdate("INSERT INTO " + TABLE_NAME + " (" + ID_FIELD_NAME + ", "
			// + ASKER_FIELD_NAME + ", " + OWNER_FIELD_NAME + ", " + TITLE_FIELD_NAME + ", "
			// + DESCRIPTION_FIELD_NAME + ", " + CREATION_DATE_FIELD_NAME + ", " + DEADLINE_FIELD_NAME + ", "
			// + PRIORITY_FIELD_NAME + ", " + DONE_FIELD_NAME + ") values (" + task.getId() + ", '"
			// + task.getAsker() + "', '" + task.getOwner() + "', '" + task.getTitle() + "', '"
			// + task.getDescription() + "', " + new Date().getTime() + ", " + task.getDeadline().getTime() + ", "
			// + task.getPriority() + ", " + (task.isDone() ? IS_TASK_DONE : IS_TASK_NOT_DONE) + ")");
		}

		return result;
	}

	public boolean deleteAllTasks() {
		if (DELETE_ALL_TASKS_PSTATEMENT == null) {
			DELETE_ALL_TASKS_PSTATEMENT = DBManager.INSTANCE.prepareStatement("DELETE FROM " + TABLE_NAME);
		}
		boolean result;
		try {
			result = DELETE_ALL_TASKS_PSTATEMENT.executeUpdate() >= 1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while deleting all tasks", e);
			result = false;
		}
		if (result) {
			lastId = Task.NO_ID;
		}
		return result;
	}
}
