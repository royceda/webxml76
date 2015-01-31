package fr.enseirb.webxml.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.enseirb.webxml.data.dao.DBManager;
import fr.enseirb.webxml.data.xml.XMLMediator;
import fr.enseirb.webxml.util.ServletToolkit;
import fr.enseirb.webxml.util.XMLToolkit;

/**
 * Servlet implementation class ImportTasksServlet
 */
public class ReinitDataServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ReinitDataServlet.class.getName());

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReinitDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String postData = ServletToolkit.getPostData(request);

		LOGGER.log(Level.INFO, "Erasing previous data");
		DBManager.INSTANCE.getTaskDAO().deleteAllTasks();
		DBManager.INSTANCE.getUserDAO().deleteAllUSers();

		LOGGER.log(Level.INFO, "Loading new data");
		boolean success = XMLMediator.importXMLTasks(postData);
		String message;
		if (!success) {
			message = "La base de données n'a pas été réinitialisée";

		} else {
			message = "La base de données a été réinitialisée";

		}

		ServletToolkit.writeResponse(response, XMLToolkit.createPostResult(message, success));
	}
}
