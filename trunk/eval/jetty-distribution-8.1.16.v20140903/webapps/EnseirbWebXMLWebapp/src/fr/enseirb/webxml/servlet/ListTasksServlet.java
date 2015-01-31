package fr.enseirb.webxml.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.enseirb.webxml.data.xml.XMLMediator;
import fr.enseirb.webxml.util.ServletToolkit;


/**
 * Servlet implementation class AboutServlet
 */
public class ListTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListTasksServlet() {
      super();
      }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse = "";
	String id = params.getProperty("id");

	   


	if(params.containsKey("id")){
	    int i = Integer.parseInt(id);
	    response.setHeader("Content-Type", "application/xml");
	    sResponse = XMLMediator.getTask(i); 
	}
	else {
	    response.setHeader("Content-Type", "application/xml");
	    sResponse = XMLMediator.getTasks(); 
	}
	
	ServletToolkit.writeResponse(response, sResponse);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
    }
}
    
