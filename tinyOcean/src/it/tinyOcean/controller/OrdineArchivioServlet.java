package it.tinyOcean.controller;

import java.io.IOException;
import java.time.LocalDate;
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

/**
 * Servlet implementation class OrdineArchivioServlet
 */
@WebServlet("/OrdineArchivio")
public class OrdineArchivioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineArchivioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		OrdineDAO orderDAO = new OrdineDAO();
		List<OrdineBean> ordini = new ArrayList<OrdineBean>();
		
		if (request.getParameter("startdate") != null && request.getParameter("enddate") != null)
			ordini = orderDAO.getAllOrders(LocalDate.parse(request.getParameter("startdate")),
					LocalDate.parse(request.getParameter("enddate")));
		else if(request.getParameter("username")!= null)
			ordini = orderDAO.getAllOrdersByUsername(request.getParameter("username"));
		else
			ordini=orderDAO.getAllOrders();
		session.setAttribute("ordini", ordini);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/ordini.jsp");
		dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
