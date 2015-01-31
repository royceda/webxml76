package fr.enseirb.webxml.servlet;

//import fr.enseirb.webxml.data.xml;

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
public class CreateUserServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String user = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet2() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse            = "init";
	String name                 = params.getProperty("name");
	String userXML              = "";

	//get user
	if(params.containsKey("post")){
	    
	    user = ServletToolkit.getPostData(request);
	    userXML = "<user name=\""+user+"\"/>";
	    
	    XMLMediator.addUser(userXML);
        
	    
	    Map<String, String> xslParams = new HashMap<String, String>();
	    xslParams.put("Test create", "Ajout");
	    xslParams.put("create user", "user");
	    
	    sResponse = XMLToolkit.transformXML(userXML,
						"/resources/xsl/common/create_user.xsl",
	    					xslParams);
	    
	}
	else {
	    userXML = "<user name=\""+name+"\"/>";
	    Map<String, String> xslParams = new HashMap<String, String>();
	    xslParams.put("Test create", "Ajout");
	    xslParams.put("create user", "user");
	    
	    sResponse = XMLToolkit.transformXML(userXML,
						"/resources/xsl/common/create_user.xsl",
						xslParams);
	}
	ServletToolkit.writeResponse(response, sResponse);
    }
        
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
    }

}

