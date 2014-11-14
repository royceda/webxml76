package fr.enseirb.webxml.data.dao.gae;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enseirb.webxml.data.dao.IUserDAO;
import fr.enseirb.webxml.data.model.User;

public class GAEUserDAO implements IUserDAO {
	private Map<String, User> users = new HashMap<String, User>();

	public List<User> getUsers() {
		List<User> sortedList = new ArrayList<User>(users.values());
		Collections.sort(sortedList, new Comparator<User>() {
			public int compare(User o1, User o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return sortedList;
	}

	public boolean addUser(User user) {
		if (users.containsKey(user.getName())) {
			return false;
		} else {
			users.put(user.getName(), user);
			return true;
		}
	}

	public boolean deleteAllUSers() {
		users = new HashMap<String, User>();
		return true;
	}
}
