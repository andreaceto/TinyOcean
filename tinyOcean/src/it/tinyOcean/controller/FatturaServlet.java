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

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import it.tinyOcean.model.Cart;
import it.tinyOcean.model.ContenutoBean;
import it.tinyOcean.model.OrdineBean;
import it.tinyOcean.model.OrdineDAO;
import it.tinyOcean.model.UtenteBean;
import it.tinyOcean.model.UtenteDAO;

/**
 * Servlet implementation class FatturaServlet
 */
@WebServlet("/Fattura")
public class FatturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FatturaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		OrdineDAO dao = new OrdineDAO();
		UtenteBean user = (UtenteBean) session.getAttribute("currentSessionUser");
		String indirizzo = ((LinkedList<String>) session.getAttribute("indirizzi")).get(0);
		String metodoPagamento = ((ArrayList<String>) session.getAttribute("metodi")).get(0);
		//rilascio fattura
		OrdineBean ordine = new OrdineBean();
		if(user.getNome()=="guest") {
			ordine = dao.getAllOrdersByUser(user).getFirst();
		}else {
			ordine = dao.getAllOrdersByUser(user).getLast();
		}
		
		double imponibile = 0.0;
		
		String percorsoPDF = getServletContext().getRealPath("/")+"WEB-INF/Fatture";
		
		File file = new File(percorsoPDF + ordine.getNumOrdine() + ".pdf"); 
		
		//inizio parte della fattura
		
        PdfWriter pdfWriter = new PdfWriter(percorsoPDF + ordine.getNumOrdine() + ".pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        
//        String imgPath = getServletContext().getRealPath("/") + "img";
//        ImageData imagedata = ImageDataFactory.create(imgPath);
//        Image image = new Image(imagedata);
//        image.setHeight((float) 75);

        pdfDocument.setDefaultPageSize(PageSize.A4);
        float x = pdfDocument.getDefaultPageSize().getWidth() / 2;
        float y = pdfDocument.getDefaultPageSize().getHeight() / 2;

       //inizio creazione del pdf
        Document document = new Document(pdfDocument);
        float threecol = 190f;
        float colonnaUno = 85f;
        float colonnaPerTitolo = 216f;
        //array dove ogni elemento indica la dimensione della i-esima colonna
        float threeColumnWidth2[] = {colonnaUno, colonnaPerTitolo, threecol};
        float twocol = 285f;
        float twocol150 = 550f; //435f start point
        float twoColumnWidth[] = {twocol150, twocol};
        float threeColumnWidth[] = {threecol, threecol, threecol};
        float fullWidth[] = {threecol * 3};
        
        Color blu = new DeviceRgb(53, 79, 134);
        Color bianco = new DeviceRgb(244,246,252);

        //il costruttore della tabella prende in input l'array di float
        Table headerTable = new Table(threeColumnWidth2);
//        headerTable.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        //paragraph Ã¨ come <p> di html
        headerTable.addCell(new Cell().add(new Paragraph("TinyOcean").setFontSize(38).setMarginTop(2)).setBorder(Border.NO_BORDER));
        headerTable.addCell(new Cell().add( new Paragraph("TinyOcean,\nVia Via Giovanni Paolo II\n Fisciano (NA),\nTel: 089961111\nsupport@tinyOcean.com")).setBorder(Border.NO_BORDER));
        //aggiunge al documento la tabella
        document.add(headerTable);

        //linea di separazione come <hr> di html
        LineSeparator ls = new LineSeparator(new SolidLine(1f));
        ls.setMarginTop((float) 2.0);
        document.add(ls);

        //descrizione fattura
        Table descrizione = new Table(twoColumnWidth);
        descrizione.setMarginTop((float) 10.0);
        descrizione.addCell(new Cell().add(new Paragraph(("Fattura")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));
        descrizione.addCell(new Cell().add(new Paragraph(("Destinatario")).setTextAlignment(TextAlignment.CENTER)).setBold().setBackgroundColor(blu));
        descrizione.addCell(new Cell().add(new Paragraph("Fattura numero: "+ordine.getNumOrdine()+"\nData: "+ordine.getDataSpedizione()).setMarginLeft(2).setMarginTop(1)));
        
        UtenteDAO uDao = new UtenteDAO();
        
		if(user.getNome() != "guest") {
			descrizione.addCell(new Cell().add(new Paragraph("Spettabile "+ user.getNome() + " " + user.getCognome()+"\n"+user.getNumTel()).setMarginLeft(2).setMarginTop(1)));
	        document.add(descrizione);
		}
        //linea di separazione
        ls.setMarginTop((float) 10.0);
        document.add(ls);

        //prodotti acquistati
        Table prodotti = new Table(threeColumnWidth);
        prodotti.setMarginTop(10);
        //intestazione tabella prodotti
        prodotti.addCell(new Cell().add(new Paragraph(("Prodotto")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));
        prodotti.addCell(new Cell().add(new Paragraph(("Quantita' ")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));
        prodotti.addCell(new Cell().add(new Paragraph(("Costo")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));

        for(ContenutoBean c : dao.getContentByOrderId(String.valueOf(ordine.getNumOrdine()))) { 
        	imponibile += c.getImponibile();
        	prodotti.addCell(new Cell().add(new Paragraph(c.getNomeArt()).setMarginLeft(2).setMarginTop(1)));
        	prodotti.addCell(new Cell().add(new Paragraph(String.valueOf(c.getQuantita())).setMarginLeft(2).setMarginTop(1)));
        	prodotti.addCell(new Cell().add(new Paragraph("€ " + String.valueOf(c.getPrezzoAcq())).setMarginLeft(2).setMarginTop(1)));
        }
        
        
        document.add(prodotti);

        //linea di separazione
        ls.setMarginTop((float) 20.0);
        document.add(ls);

        //DETTAGLI FISCALI
        Table dettagliFiscali = new Table(twoColumnWidth);
        dettagliFiscali.setMarginTop(10);

        dettagliFiscali.addCell(new Cell().add(new Paragraph(("Metodo Pagamento")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));
        dettagliFiscali.addCell(new Cell().add(new Paragraph(("Costo Totale")).setBold().setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(blu));
        

        	dettagliFiscali.addCell(new Cell().add(new Paragraph(metodoPagamento).setMarginLeft(2).setMarginTop(1)));
        	dettagliFiscali.addCell(new Cell().add(new Paragraph("Imponibile: € "+imponibile+"\nSpedizione: € 0,00\nSconto: € 0,00\nIVA: 22%\n").setMarginLeft(2).setMarginTop(1)));
        	document.add(dettagliFiscali);
 

        Table totale = new Table(fullWidth);
        totale.addCell(new Cell().add(new Paragraph("Totale: € "+ordine.getCostoTot()).setBold().setFontSize(16).setTextAlignment(TextAlignment.RIGHT).setMarginRight(25)).setBackgroundColor(bianco));
        document.add(totale);
        //note
        Table note = new Table(fullWidth);
        note.setFixedPosition(20, 0 , threecol*3);
        note.addCell(new Cell().add(new Paragraph("Grazie per averci preferito!")).setBorder(Border.NO_BORDER));
        document.add(note);

        document.close(); //close salva		    
	    
        response.setContentType("application/pdf");
	    response.setHeader( "Content-Disposition", "attachment; filename=\"fattura.pdf\"");
	    
	    
	    
	    try(InputStream in = new FileInputStream(file)){
	            try(OutputStream out = response.getOutputStream()) {
	            	byte[] buffer = new byte[1024];
	          
	              int numBytesRead;
	              while ((numBytesRead = in.read(buffer)) > 0) {
	                  out.write(buffer, 0, numBytesRead);
	              }
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
