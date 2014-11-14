package fr.enseirb.webxml.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.enseirb.webxml.util.ServletToolkit;

/**
 * Servlet implementation class RawFileServlet
 */
public class RawFileServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(RawFileServlet.class.getName());
	
	private static final long serialVersionUID = 1L;

	private static final String[] FILES_DIRS = new String[] { "jquery", "js/", "css/", "images/" };

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RawFileServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int splitIndex = -1;

		for (int i = 0; i < FILES_DIRS.length; i++) {
			splitIndex = uri.indexOf(FILES_DIRS[i]);
			if (splitIndex > 0) {
				break;
			}
		}

		byte[] bResponse;
		if (splitIndex > 0) {
			String filePath = "/resources/" + uri.substring(splitIndex);
			bResponse = ServletToolkit.readRawFile(filePath);

		} else {
			LOGGER.log(Level.INFO, "Unable to locate file: " + uri);
			bResponse = new byte[0];
		}

		response.getOutputStream().write(bResponse);
	}

}
