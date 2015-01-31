package fr.enseirb.webxml.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.enseirb.webxml.util.ServletToolkit;
import fr.enseirb.webxml.data.xml.XMLMediator;

/**
 * Servlet implementation class DefaultServlet
 */
public class CRUDTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CRUDTaskServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse = new String("Task Create");
	if (request.getRequestURI().contains("task/create/url")) {
	    String taskXML = "<task";
	    String buffer;

	    if (params.containsKey("id")) {
		sResponse = "Task Modified";
		buffer = params.getProperty("id","err");
		taskXML += " id=\""+buffer+"\"";
	    }
	    else {
		sResponse = "Task created";
	    }
	    if (params.containsKey("title")) {
		buffer = params.getProperty("title","err");
		taskXML += " title=\""+buffer+"\"";
	    }
	    if (params.containsKey("creationDate")) {
		buffer = params.getProperty("creationDate","err");
		taskXML += " creationDate=\""+buffer+"\"";
	    }
	    if (params.containsKey("deadline")) {
		buffer = params.getProperty("deadline","err");
		taskXML += " deadline=\""+buffer+"\"";
	    }
	    if (params.containsKey("priority")) {
		buffer = params.getProperty("priority","err");
		taskXML += " priority=\""+buffer+"\"";
	    }
	    if (params.containsKey("done")) {
		buffer = params.getProperty("done","err");
		taskXML += " done=\""+buffer+"\"";
	    }
	    taskXML += ">\n";

	    if (params.containsKey("description")) {
		buffer = params.getProperty("description","err");
		taskXML += "\t<description>\n\t"+buffer+"\n\t</description>";
	    }
	    taskXML += "\n";
	    if (params.containsKey("asker")) {
		buffer = params.getProperty("asker","err");
		taskXML += "\t<asker>"+buffer+"</asker>";
	    }
	    taskXML += "\n";
	    if (params.containsKey("owner")) {
		buffer = params.getProperty("owner","err");
		taskXML += "\t<owner>"+buffer+"</owner>";
	    }
	    taskXML += "\n";
	    taskXML += "</task>";
	    String task = new String(taskXML);
	    if (!XMLMediator.addOrModifyTask(task))
		sResponse = "error" + task;
	    else
		sResponse = "task Created";
	}
	ServletToolkit.writeResponse(response, sResponse);
    }
}
