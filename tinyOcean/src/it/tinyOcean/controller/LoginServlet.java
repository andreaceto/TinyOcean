package it.tinyOcean.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.tinyOcean.model.*;
import javax.servlet.annotation.*;
@WebServlet("/Login")

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static UtenteDAO UserDAO = new UtenteDAO();

public void doGet(HttpServletRequest request, HttpServletResponse response) 
			           throws ServletException, java.io.IOException {

try
{	    

     UtenteBean user = new UtenteBean();
     user.setUsername(request.getParameter("un"));
     user.setPassword(request.getParameter("pw"));
     user = UtenteDAO.doRetrieve(user);
	   		    
     if (user.isValid())
     {
	        
          HttpSession session = request.getSession(true);	    
          session.setAttribute("currentSessionUser",user); 
          response.sendRedirect("Homepage.jsp"); //logged-in page      		
     }
	        
     else 
          response.sendRedirect("loginPage.jsp?login=wrong"); //error page 
} 
		
		
catch (Throwable theException) 	    
{
     System.out.println(theException); 
}
       }
	}
