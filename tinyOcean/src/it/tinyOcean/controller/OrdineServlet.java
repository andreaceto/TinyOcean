package it.tinyOcean.controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tinyOcean.model.*;

/**
 * Servlet implementation class OrdineServlet
 */
@WebServlet("/Ordine")
public class OrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		OrdineDAO order = new OrdineDAO();

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equalsIgnoreCase("CompletaOrdine")) {
				HttpSession session = request.getSession(true);
				order.doSave((UtenteBean) session.getAttribute("currentSessionUser"), request.getParameter("indirizzo"),
						request.getParameter("pagamento"), cart);
				session.setAttribute("cart", new Cart());
				response.sendRedirect("Homepage.jsp");

			}

			else if (action.equalsIgnoreCase("mostradettagli")) {
				HttpSession session = request.getSession(true);
				String id = String.valueOf(request.getParameter("codice"));
				LinkedList<ContenutoBean> products=order.getContentByOrderId(id);
				session.removeAttribute("ordine");
				session.setAttribute("ordine", order.getOrderById(id));
				session.setAttribute("products", products);
				request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
