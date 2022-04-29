package it.tinyOcean.controller;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.tinyOcean.model.*;

/**
 * Servlet implementation class UserControlServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static UtenteDAO utenteDAO = new UtenteDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("usr");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cogn");
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		String numTel = request.getParameter("num_tel");
		String paeseResidenza = request.getParameter("paese");
		LocalDate dataNascita = LocalDate.parse(request.getParameter("data"));
		UtenteBean user = new UtenteBean();
		user.setUsername(username);
		user.setNome(nome);
		user.setCognome(cognome);
		user.setEmail(email);
		user.setPassword(password);
		user.setNumTel(numTel);
		user.setPaeseResidenza(paeseResidenza);
		user.setDataNascita(dataNascita);
		utenteDAO.doSave(user);

		response.sendRedirect("loginPage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
