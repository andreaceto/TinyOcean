package it.tinyOcean.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tinyOcean.model.IndirizzoBean;
import it.tinyOcean.model.IndirizzoDAO;
import it.tinyOcean.model.MetodoPagamentoBean;
import it.tinyOcean.model.MetodoPagamentoDAO;
import it.tinyOcean.model.UtenteBean;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPage")
public class UserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPageServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UtenteBean user = (UtenteBean) session.getAttribute("currentSessionUser");
		IndirizzoDAO addressDAO = new IndirizzoDAO();
		MetodoPagamentoDAO paymentMethodDAO = new MetodoPagamentoDAO();
		
		List<IndirizzoBean> addresses = addressDAO.getAddresses(user);
		List<String> indirizzi = new LinkedList<String>();
		
		for (IndirizzoBean address : addresses) {
			String indirizzo;
			indirizzo = address.getVia() + " " + address.getNumCivico() + " " + address.getCitta() + " "
			+ address.getProvincia() + " " + address.getCap();

			indirizzi.add(indirizzo);
		}

	
		List<MetodoPagamentoBean> paymentMethods = paymentMethodDAO.getPaymentMethods(user);
		List<String> metodiPagamento = new ArrayList<String>();
		
		for (MetodoPagamentoBean paymentMethod : paymentMethods) {
			String metodoPagamento;
			metodoPagamento = paymentMethod.getNumCarta() + " " + paymentMethod.getTipo() + " " 
			+ paymentMethod.getTitolare() + " " + paymentMethod.getScadenza() + " "
			+ paymentMethod.getIndirizzoFatt();

			metodiPagamento.add(metodoPagamento);
		}
		session.setAttribute("indirizzi", indirizzi);
		session.setAttribute("metodi", metodiPagamento);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/userPage.jsp");
		dispatcher.include(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
