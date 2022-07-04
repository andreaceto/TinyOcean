package it.tinyOcean.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.*;

import it.tinyOcean.model.ArticoloBean;
import it.tinyOcean.model.RecensioneBean;
import it.tinyOcean.model.UtenteBean;
import it.tinyOcean.model.ArticoloDAO;
import it.tinyOcean.model.RecensioneDAO;
import it.tinyOcean.model.ResponseStatusMessage;

/**
 * Servlet implementation class RecensioneServlet
 */
@WebServlet("/Recensione")
public class RecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecensioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		UtenteBean user = (UtenteBean) session.getAttribute("currentSessionUser");
		
		if(user==null)
		{
			RequestDispatcher view = request.getRequestDispatcher("/loginPage.jsp");
			view.include(request, response);
			
		}	
		if(action.equals("recensisci")) {
			RecensioneBean recensione = new RecensioneBean();
			RecensioneDAO recensioneDAO = new RecensioneDAO();
			ArticoloBean prodotto;
			ArticoloDAO productDao = new ArticoloDAO();
			try {
				prodotto = productDao.doRetrieveByKey(Integer.valueOf(request.getParameter("articolo")));
				recensione.setUtente(user);
				recensione.setArticolo(prodotto);
				recensione.setCommento(request.getParameter("commento"));
				recensione.setVoto(Integer.valueOf(request.getParameter("voto")));
				recensione.setDataPubblicazione(LocalDate.now());
						
				recensioneDAO.doSave(recensione);
						
//				request.setAttribute("prodotto", prodotto);
//						
//				RequestDispatcher view = request.getRequestDispatcher("/Articolo?action=read&id=" + request.getParameter("articolo"));
//				view.forward(request, response);
				
				response.sendRedirect("Articolo?action=read&id=" + request.getParameter("articolo"));
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action.equals("check")) {
			ArticoloBean prodotto;
			ArticoloDAO productDao = new ArticoloDAO();
			RecensioneDAO recensioneDAO = new RecensioneDAO();
			
			try {
				String messaggio = new String();
				prodotto = productDao.doRetrieveByKey(Integer.valueOf(request.getParameter("id")));
				boolean acquired = false;
				boolean reviewed = false;
				
				if(user!=null) {
					acquired = ArticoloDAO.isAcquired(prodotto, user);
					reviewed = RecensioneDAO.isAlreadyReviewed(prodotto, user);
				}
				else {
					messaggio ="non recensibile";
				}
				
				if(acquired && !reviewed){
					messaggio = "recensibile";
				}
				
				else if(!acquired || user==null || reviewed) {
					messaggio = "non recensibile";
				}

					response.setStatus(200);
					response.getWriter().print(gson.toJson(new ResponseStatusMessage(200, messaggio)));
					response.getWriter().flush();
				}	
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
