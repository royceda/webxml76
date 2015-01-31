package fr.enseirb.webxml.servlet;

//import fr.enseirb.webxml.data.xml;

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
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String teacher = "Lombard";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse            = "init";
	String name                 = params.getProperty("name");
	
	   
	//User Creator
	if(request.getRequestURI().contains("user/create/url")){
	    if(params.containsKey("name")){
		String userXML = "<user name=\""+name+"\"/>";
	    
		if(XMLMediator.addUser(userXML) )
		    sResponse = "Youpiiiiii !!!!! *_*";
		else
		    sResponse = "merde, ça ne marche pas !!!";
	    }
	}
	
	ServletToolkit.writeResponse(response, sResponse);
    }
}

