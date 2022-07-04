package it.tinyOcean.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import it.tinyOcean.model.ArticoloBean;
import it.tinyOcean.model.ArticoloDAO;
import it.tinyOcean.model.RecensioneBean;
import it.tinyOcean.model.RecensioneDAO;
import it.tinyOcean.model.Cart;


/**
 * Servlet implementation class ProductControl
 */
@WebServlet("/Articolo")
public class ArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static ArticoloDAO model = new ArticoloDAO();

	public ArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("addC")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.addProduct(id);
				} else if (action.equalsIgnoreCase("deleteC")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.deleteProduct(id);
					request.getRequestDispatcher("cart.jsp").forward(request, response);
				}else if (action.equalsIgnoreCase("read")) {
					int id = Integer.parseInt(request.getParameter("id"));
					request.removeAttribute("product");
					request.setAttribute("product", model.doRetrieveByKey(id));
					
					RecensioneDAO daoRecensioni = new RecensioneDAO();
					ArrayList<RecensioneBean> elencoRecensioni = new ArrayList<>();
					ArticoloBean product;
					try {
						product = model.doRetrieveByKey(Integer.valueOf(request.getParameter("id")));
						elencoRecensioni = (ArrayList<RecensioneBean>) daoRecensioni.getRecensioniByProduct(product).stream().limit(5).collect(Collectors.toList());
						
						request.setAttribute("votoMedio", RecensioneDAO.getVotoMedio(product));
						request.setAttribute("recensioni", elencoRecensioni);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					request.getRequestDispatcher("/productDetails.jsp").forward(request, response);
					
				} else if (action.equalsIgnoreCase("update")) {
					int id = Integer.parseInt(request.getParameter("id"));
					ArticoloBean product = new ArticoloBean();
					product = model.doRetrieveByKey(id);
					Integer newId = Integer.parseInt(request.getParameter("newID"));
					String produttore = request.getParameter("produttore");
					String nome = request.getParameter("nome");
					String descrizione = request.getParameter("descrizione");
					Float prezzo = Float.parseFloat(request.getParameter("prezzo"));
					Integer stock = Integer.parseInt(request.getParameter("stock"));
					Float peso = Float.parseFloat(request.getParameter("peso"));
					Float altezza = Float.parseFloat(request.getParameter("altezza"));
					Float larghezza = Float.parseFloat(request.getParameter("larghezza"));
					Float lunghezza = Float.parseFloat(request.getParameter("lunghezza"));
					Integer saldo = Integer.parseInt(request.getParameter("saldo"));
					
					if (newId != null) {
						product.setId(newId);
					}
					
					if (produttore != null) {
						product.setProduttore(produttore);
					}
					
					if (nome != null) {
						product.setNome(nome);
					}
					
					if (descrizione != null) {
						product.setDescrizione(descrizione);
					}
					
					if (prezzo != null) {
						product.setPrezzo(prezzo);
					}

					if (stock != null) {
						product.setStock(stock);
					}
					
					if (peso != null) {
						product.setPeso(peso);
					}
					
					if (altezza != null) {
						product.setAltezza(altezza);
					}
					
					if (larghezza != null) {
						product.setLarghezza(larghezza);
					}
					
					if (lunghezza != null) {
						product.setPeso(lunghezza);
					}
					
					if (saldo != null) {
						product.setSaldo(saldo);
					}

					model.Alter(id, product);

				}

				else if (action.equalsIgnoreCase("delete")) {
					int id = Integer.parseInt(request.getParameter("id"));
					model.doDelete(id);
				} else if (action.equalsIgnoreCase("insert")) {

					Integer id = Integer.parseInt(request.getParameter("id"));
					String produttore = request.getParameter("produttore");
					String nome = request.getParameter("nome");
					String descrizione = request.getParameter("descrizione");
					Float prezzo = Float.parseFloat(request.getParameter("prezzo"));
					Integer stock = Integer.parseInt(request.getParameter("stock"));
					Float peso = Float.parseFloat(request.getParameter("peso"));
					Float altezza = Float.parseFloat(request.getParameter("altezza"));
					Float larghezza = Float.parseFloat(request.getParameter("larghezza"));
					Float lunghezza = Float.parseFloat(request.getParameter("lunghezza"));
					Integer saldo = Integer.parseInt(request.getParameter("saldo"));
					
					ArticoloBean bean = new ArticoloBean();
					
					bean.setId(id);
					bean.setProduttore(produttore);
					bean.setNome(nome);
					bean.setDescrizione(descrizione);
					bean.setPrezzo(prezzo);
					bean.setStock(stock);
					bean.setPeso(peso);
					bean.setAltezza(altezza);
					bean.setLarghezza(larghezza);
					bean.setLunghezza(lunghezza);
					bean.setSaldo(saldo);
					
					model.doSave(bean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);
		
		String sort = request.getParameter("sort");
		
			try {
				request.removeAttribute("products");
				request.setAttribute("products", model.doRetrieveAll(sort));
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}		
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Homepage.jsp");
			dispatcher.include(request, response);



	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

