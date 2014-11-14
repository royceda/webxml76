package fr.enseirb.webxml.data.dao;

import java.util.List;

import fr.enseirb.webxml.data.model.User;

public interface IUserDAO {
	public List<User> getUsers();

	public boolean addUser(User user);

	public boolean deleteAllUSers();
}
