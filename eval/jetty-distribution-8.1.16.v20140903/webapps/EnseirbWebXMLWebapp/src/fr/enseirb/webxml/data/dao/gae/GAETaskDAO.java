package fr.enseirb.webxml.data.dao.gae;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enseirb.webxml.data.dao.ITaskDAO;
import fr.enseirb.webxml.data.model.Task;

public class GAETaskDAO implements ITaskDAO {
	private Map<Integer, Task> tasks = new HashMap<Integer, Task>();

	private int lastIndex = Task.NO_ID;

	public List<Task> getTasks() {
		List<Task> sortedList = new ArrayList<Task>(tasks.values());
		Collections.sort(sortedList, new Comparator<Task>() {
			public int compare(Task o1, Task o2) {
				return new Integer(o1.getId()).compareTo(new Integer(o2.getId()));
			}
		});
		return sortedList;
	}

	public Task getTask(int id) {
		return tasks.get(id);
	}

	public boolean addOrModify(Task task) {
		int indexToAdd;
		if (task.getId() > Task.NO_ID) {
			// modification
			indexToAdd = task.getId();

		} else {
			// ajout
			indexToAdd = ++lastIndex;
			task.setId(indexToAdd);
			task.setCreationDate(new Date());

		}
		tasks.put(new Integer(indexToAdd), task);
		return true;
	}

	public boolean deleteAllTasks() {
		tasks = new HashMap<Integer, Task>();
		lastIndex = Task.NO_ID;
		return true;
	}
}
