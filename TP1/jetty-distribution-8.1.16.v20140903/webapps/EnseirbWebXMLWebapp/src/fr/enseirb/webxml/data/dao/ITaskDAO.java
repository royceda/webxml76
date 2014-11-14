package fr.enseirb.webxml.data.dao;

import java.util.List;

import fr.enseirb.webxml.data.model.Task;

public interface ITaskDAO {
	public List<Task> getTasks();

	public Task getTask(int id);

	public boolean addOrModify(Task task);
	
	public boolean deleteAllTasks();
}
