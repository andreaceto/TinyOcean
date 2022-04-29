package it.tinyOcean.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tinyOcean.model.UtenteBean;
import it.tinyOcean.model.UtenteDAO;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdate")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");
			LocalDate data =LocalDate.parse(request.getParameter("data"));
			HttpSession session = request.getSession(false);	    
	         UtenteBean user= (UtenteBean) session.getAttribute("currentSessionUser");
	         UtenteDAO dao= new UtenteDAO();
	         user.setNome(nome);
	         user.setCognome(cognome);
	         user.setEmail(email);
	         user.setDataNascita(data);
	         dao.Alter(user.getUsername(), user);
	         response.sendRedirect("Homepage.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
