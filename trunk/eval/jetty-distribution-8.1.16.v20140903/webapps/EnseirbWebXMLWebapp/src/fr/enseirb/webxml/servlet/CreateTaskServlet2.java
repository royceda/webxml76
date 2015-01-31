package fr.enseirb.webxml.servlet;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;


import fr.enseirb.webxml.data.xml.XMLMediator;
import fr.enseirb.webxml.util.ServletToolkit;
import fr.enseirb.webxml.util.XMLToolkit;

/**
 * Servlet implementation class AboutServlet
 */
public class CreateTaskServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTaskServlet2() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params    = ServletToolkit.parseURLParams(request);
	String               sResponse = "init";	   
	String               taskXML   = XMLMediator.getTask(1);
	String               pageState = "";
	String               id        = "";
	
	if(params.containsKey("create")){
	    pageState = "CREATE";
	}
	if(params.containsKey("modify")){
	    id = params.getProperty("id");
	    pageState = "MODIFY";
	    taskXML = XMLMediator.getTask(Integer.parseInt(id));
	}
	if(params.containsKey("view")){
	    id = params.getProperty("id");
	    pageState = "READONLY";
	    taskXML = XMLMediator.getTask(Integer.parseInt(id));
	}

	Map<String, String> xslParams = new HashMap<String, String>();
	xslParams.put("pageTitle", "Ajout utilisateur");
	xslParams.put("htmlTitle", "tache à ajouter");
	xslParams.put("pageState", pageState);
	sResponse = XMLToolkit.transformXML(taskXML,
					    "/resources/xsl/common/crud_task.xsl",
					    xslParams);
	
	ServletToolkit.writeResponse(response, sResponse);
    }
    
}
