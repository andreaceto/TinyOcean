package it.tinyOcean.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.tinyOcean.model.ArticoloBean;
import it.tinyOcean.model.ArticoloDAO;

/**
 * Servlet implementation class CatalogueUpdate
 */
@WebServlet("/CatalogoUpdate")
public class CatalogoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CatalogoUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArticoloBean newProduct = new ArticoloBean();
		List<String> parameterNames = new ArrayList<String>(request.getParameterMap().keySet());
		
		for(String nome : parameterNames)
		{
			System.out.println(nome);
		}
		
		newProduct.setId(Integer.parseInt(request.getParameter("newId")));
		newProduct.setProduttore(request.getParameter("produttore"));
		newProduct.setNome(request.getParameter("nome"));
		newProduct.setDescrizione(request.getParameter("descrizione"));
		newProduct.setPrezzo(Float.parseFloat(request.getParameter("prezzo").replace(',', '.')));
		newProduct.setStock(Integer.parseInt(request.getParameter("stock")));
		newProduct.setPeso(Float.parseFloat(request.getParameter("peso")));
		newProduct.setAltezza(Float.parseFloat(request.getParameter("altezza")));
		newProduct.setLarghezza(Float.parseFloat(request.getParameter("larghezza")));
		newProduct.setLunghezza(Float.parseFloat(request.getParameter("lunghezza")));
		newProduct.setSaldo(Integer.parseInt(request.getParameter("saldo")));
		
		ArticoloDAO productDAO = new ArticoloDAO();
		productDAO.Alter(Long.parseLong( request.getParameter("oldId")) , newProduct);
	}

}

