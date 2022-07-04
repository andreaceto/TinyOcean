package it.tinyOcean.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

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
		OrdineDAO dao = new OrdineDAO();

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equalsIgnoreCase("CompletaOrdine")) {
				HttpSession session = request.getSession(true);
				
				UtenteBean user = (UtenteBean) session.getAttribute("currentSessionUser");
				String indirizzo = ((LinkedList<String>) session.getAttribute("indirizzi")).get(0);
				String metodoPagamento = ((ArrayList<String>) session.getAttribute("metodi")).get(0);
				
				dao.doSave(user, indirizzo, metodoPagamento, cart);
				session.setAttribute("cart", new Cart());

				response.sendRedirect("ringraziamenti.jsp");
			}

			else if (action.equalsIgnoreCase("mostradettagli")) {
				HttpSession session = request.getSession(true);
				String id = String.valueOf(request.getParameter("codice"));
				LinkedList<ContenutoBean> products=dao.getContentByOrderId(id);
				session.removeAttribute("ordine");
				session.setAttribute("ordine", dao.getOrderById(id));
				session.setAttribute("products", products);
				request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
			}
		}else {
					System.out.println("action == null");
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
