package fr.enseirb.webxml.data.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4958654056653758134L;

	public static final int NO_ID = -1; // indique qu'aucun id n'est encore affecte

	private int id = NO_ID; // identifiant unique
	private String asker = "";// nom de la personne demandant l'action
	private String owner = "";// nom de la personne devant faire l'action
	private String title = "";
	private String description = "";
	private Date creationDate = new Date(); // date a laquelle l'action a ete creee
	private Date deadline = new Date(); // date a laquelle l'action doit etre faite
	private int priority = 0; // importance de l'action
	private boolean done = false; // indique si la tache est terminee ou non

	public Task() {
	}

	public Task(int id, String asker, String owner, String title, String description, Date creationDate, Date deadline,
			int priority) {
		super();
		this.id = id;
		this.asker = asker;
		this.owner = owner;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.deadline = deadline;
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAsker() {
		return asker;
	}

	public void setAsker(String asker) {
		this.asker = asker;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
