package it.tinyOcean.controller;

import java.io.IOException;
import java.time.LocalDate;
import it.tinyOcean.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PaymentMethodControl
 */
@WebServlet("/MetodoPagamentoServlet")
public class MetodoPagamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static MetodoPagamentoDAO MetodoPagamentoDAO = new MetodoPagamentoDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MetodoPagamentoServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int tipo = (request.getParameter("tipo")=="carta di credito" ) ? 0: 1;
		long numCarta = Long.parseLong(request.getParameter("numCarta"));
		LocalDate scadenza = LocalDate.parse(request.getParameter("scadenza"));
		String titolare = request.getParameter("titolare");
		String indirizzoFatt = request.getParameter("indirizzoFatt");
		boolean predefinito = false;
		
		MetodoPagamentoBean paymentMethod = new MetodoPagamentoBean();
		
		UtenteBean user = (UtenteBean) request.getSession().getAttribute("currentSessionUser");
		paymentMethod.setTipo(tipo);
		paymentMethod.setNumCarta(numCarta);
		paymentMethod.setScadenza(scadenza);
		paymentMethod.setTitolare(titolare);
		paymentMethod.setIndirizzoFatt(indirizzoFatt);
		paymentMethod.setPredefinito(predefinito);
		
		MetodoPagamentoDAO.doSave(user, paymentMethod);
		
		response.sendRedirect("checkoutPage.jsp");
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
