package fr.enseirb.webxml.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.enseirb.webxml.util.StringFormatUtil;
import fr.enseirb.webxml.util.ServletToolkit;
import fr.enseirb.webxml.util.XMLToolkit;
import fr.enseirb.webxml.data.xml.XMLMediator;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



/**
 * Servlet implementation class AboutServlet
 */
public class OwnershipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String teacher = "Lombard";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnershipServlet() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse = "init";
	
	String id    = params.getProperty("id");
	String name  = params.getProperty("owner");
	String owner = params.getProperty("ownerName");

	String postData = ServletToolkit.getPostData(request);

	if(request.getRequestURI().contains("/user/ownership")){
	    
	    response.setHeader("Content-Type", "text/plain");

	    String tasks = XMLMediator.getTasks();
	    // String stats = "<tasks></tasks>";
	    String stats = StringFormatUtil.sortAndFormatAsCSV(  XMLToolkit.getXPathValues(tasks, "tasks/task[@id = \""+id+"\"]"));
	    ServletToolkit.writeResponse(response, stats);

	    ServletToolkit.writeResponse(response, stats);
	}
	
	
	else if(request.getRequestURI().contains("/task/ownership")){
	    
	    response.setHeader("Content-Type", "text/plain");

	    String tasks = XMLMediator.getTasks();
	    String stats = StringFormatUtil.sortAndFormatAsCSV(  XMLToolkit.getXPathValues(tasks, "tasks/task[@id = \""+id+"\"]/Owner"));
	    ServletToolkit.writeResponse(response, stats);
	}

	else if(request.getRequestURI().contains("/user/task/changeowner/post/isValid")){
	    sResponse = XMLToolkit.createPostResult("user/task", XMLToolkit.isXMLValid(postData, "/resources/xsd/owner.xsd"));
		}

	else if(request.getRequestURI().contains("/task/user/changeowner/post/isValid")){
	    sResponse = XMLToolkit.createPostResult("/task/user", XMLToolkit.isXMLValid(postData, "/resources/xsd/owner.xsd"));
	}
       
	else if(request.getRequestURI().contains("/task/user/changeowner")){
	    if(request.getRequestURI().contains("/post")){
		
		Document docPostData = XMLToolkit.parseDocument(postData);
		Element ownerShipElt = docPostData.getDocumentElement();
		
		int taskId = Integer.parseInt(ownerShipElt.getAttribute("taskId"));

		String userName = ownerShipElt.getAttribute("newOwnerName");

		String taskXML = XMLMediator.getTask(taskId);
		Document docTask = XMLToolkit.parseDocument(taskXML);
		Element taskElt = docTask.getDocumentElement();
		
		((Element) taskElt.getElementsByTagName("owner").item(0)).setTextContent(userName);
		
		if(XMLMediator.addOrModifyTask(taskElt))
		    sResponse = "Youpiii !!";
		else
		    sResponse = "NOOOn!!!!";
	    }
	}


	else if(request.getRequestURI().contains("/user/task/changeowner/post")){
	  		
		Document docPostData = XMLToolkit.parseDocument(postData);
		Element ownerShipElt = docPostData.getDocumentElement();
		
		int taskId = Integer.parseInt(ownerShipElt.getAttribute("taskId"));
		String userName = ownerShipElt.getAttribute("newOwnerName");

		String taskXML = XMLMediator.getTask(taskId);
		Document docTask = XMLToolkit.parseDocument(taskXML);
		Element taskElt = docTask.getDocumentElement();
		
		((Element) taskElt.getElementsByTagName("owner").item(0)).setTextContent(userName);
		
		if(XMLMediator.addOrModifyTask(taskElt))
		    sResponse = "Youpiii !!";
		else
		    sResponse = "NOOOn!!!!";

		
	}


	ServletToolkit.writeResponse(response, sResponse);
	
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
    }
}
