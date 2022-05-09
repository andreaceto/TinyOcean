package it.tinyOcean.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tinyOcean.model.OrdineBean;
import it.tinyOcean.model.OrdineDAO;
import it.tinyOcean.model.UtenteBean;



/**
 * Servlet implementation class OrdineStoriaServlet
 */
@WebServlet("/OrdineStoria")
public class OrdineStoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineStoriaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		UtenteBean user = (UtenteBean) session.getAttribute("currentSessionUser");
		OrdineDAO orderDAO = new OrdineDAO();
		
		List<OrdineBean> ordini = new ArrayList<OrdineBean>();
		ordini = orderDAO.getAllOrdersByUser(user);
		session.setAttribute("ordini", ordini);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/orderHistory.jsp");
		dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
