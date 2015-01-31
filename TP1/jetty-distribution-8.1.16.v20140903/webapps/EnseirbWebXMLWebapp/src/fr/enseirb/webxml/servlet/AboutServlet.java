package fr.enseirb.webxml.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.enseirb.webxml.util.ServletToolkit;

/**
 * Servlet implementation class AboutServlet
 */
public class AboutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String teacher = "Lombard";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AboutServlet() {
	super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	java.util.Properties params = ServletToolkit.parseURLParams(request);
	String sResponse = "init";
	String student1firstName = "Charlie";
	String student1lastName = "Brown";
	String student1fullName = student1firstName+" "+student1lastName;
	String studentsNumber = "1";
	String teacherDefault = "Lombard";
	String action = params.getProperty("action");



	//get Teacher
	if(request.getRequestURI().contains("teacher/post")){
	    teacher = ServletToolkit.getPostData(request);
	    sResponse = ServletToolkit.readFile("/resources/html/common/about_teacher.html");
	   
	}
	//About Teacher
	else if(request.getRequestURI().contains("teacher"))
	    sResponse = ServletToolkit.readFile("/resources/html/common/about_teacher.html");
	
	
	//About student
	else if ("studentsNumber".equals(action)) {
	    sResponse = studentsNumber;	    
	}
	else if ("firstName".equals(action)) {
	sResponse = student1firstName;
	}
	else if ("lastName".equals(action)) {
	    sResponse = student1lastName;
	}
	else if ("group".equals(action)) {
	    sResponse = "G4";
	}
	else if ("class".equals(action)) {
	    sResponse = "I2";
	}
	else if ("teacher".equals(action)) {
	    sResponse = teacher;
	}
	else {
	    sResponse = student1fullName;
	}

	ServletToolkit.writeResponse(response, sResponse);
	
	}
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
    }
}
