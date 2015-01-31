package fr.enseirb.webxml.data.model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1587201960898006347L;

	private String name; // nom, login de la personne - sert aussi d'id unique

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
