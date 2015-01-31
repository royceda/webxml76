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
public class CreateTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String teacher = "Lombard";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTaskServlet() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse            = "";	
	String id                   = null;
	String title                = null;
	String deadline             = null;
	String priority             = null;
	String done                 = null;
	String asker                = null;
	String owner                = null;
	String creationDate         = null;
	String description          = null;
   
	String taskXML = "<task";

	//task Creator
	if(request.getRequestURI().contains("task/create/url")){
	    if(params.containsKey("id")){ //if tache exist
		id = params.getProperty("id");
		sResponse = "Modification de "+id+":\n";
		taskXML += " id=\""+id+"\"";
	    }
	    if(params.containsKey("title")){
		title = params.getProperty("title");
		taskXML +=  " title=\""+title+"\"";
	    }
	    else{
		taskXML +=  " title=\"\"";
	    }
	    if(params.containsKey("deadline")){
		deadline = params.getProperty("deadline");
		taskXML += " deadline=\""+deadline+"\"";
	    }
	    else{
		taskXML += " deadline=\"\"";
	    }
	    if(params.containsKey("priority")){
	        priority = params.getProperty("priority");
		taskXML +=  " priority=\""+priority+"\"";
	    }
	    else {
		taskXML +=  " priority=\"1\"";
	    }
	    if(params.containsKey("done")){
	        done = params.getProperty("done");
		taskXML += " done=\""+done+"\"";
	    }
	    else {
		taskXML += " done=\"false\"";
	    }
	    if(params.containsKey("creationDate")){
		creationDate = params.getProperty("creationDate");
		taskXML += " creationDate=\""+creationDate+"\"";
	    }
	    else {
		taskXML += " creationDate=\" \"";
	    }
	    taskXML += ">";
	    if(params.containsKey("description")){
		description = params.getProperty("description");
		taskXML += "<description>"+description+"</description>";
	    }
	    else {
		taskXML += "<description> </description>";
	    }

	    if(params.containsKey("asker")){
		asker = params.getProperty("asker");
		taskXML += "<asker>"+asker+"</asker>";
	    }
	    else {
		taskXML += "<asker> </asker>";
	    }
	    if(params.containsKey("owner")){
		owner = params.getProperty("owner");
		taskXML += "<owner>"+owner+"</owner>";
	    }
	    else{
		taskXML += "<owner> </owner>";
	    }
	    taskXML += "</task>";

	    if(XMLMediator.addOrModifyTask(taskXML) )
		sResponse += "Youpiiiiii !!!!! *_*";
	    else
		sResponse += "merde, ca ne marche pas !!!\n\n"+taskXML;
	    }
	
	
	ServletToolkit.writeResponse(response, sResponse);
    }

}
