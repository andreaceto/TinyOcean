
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.tinyOcean.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.awt.image.*"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="javax.xml.*"%>


<% 
if (session == null || session.getAttribute("currentSessionUser") == null || ((UtenteBean) session.getAttribute("currentSessionUser")).getNome() == "guest") { 
%>
	<%@include file="./fragments/header.jsp"%>
<% 
} else{ 
%>
	<%@ include file="./fragments/headerLogged.jsp"%>
<% 
} 
%>

<%
Collection<?> products = (Collection<?>) request.getAttribute("products");
FotoDAO fotodao = new FotoDAO();
if (products == null) {
	response.sendRedirect("./Articolo");
	return;
}
%>

<!DOCTYPE html>
<html>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./style/style.css" rel="stylesheet" type="text/css">
	<script src="./js/photo.js"></script>
<title>Tiny Ocean</title>
</head>
<body>
	
    <div class="carousel">
    <ul class="slides">
    

  
      <input type="checkbox" name="radio-buttons" id="img-1" checked />
      <li class="slide-container">
        <div class="mySlides fade">
          <img src="img/foto1.webp" style="width:100%">
           <div class="centered"style="background-color:black;opacity:70%">REALIZZA IL TUO ACQUARIO<p>Scegli e acquista il miglior acquario che fa per te</p></div>

        </div>
        <div class="carousel-controls">
          <label for="img-3" class="prev-slide">
            <span>&lsaquo;</span>
          </label>
          <label for="img-2" class="next-slide">
            <span>&rsaquo;</span>
          </label>
        </div>
      </li>
      
      
     
      <input type="checkbox" name="radio-buttons" id="img-2" checked/>
      <li class="slide-container">
        <div class="mySlides fade">
          <img src="img/ring5.jpeg" style="width:100%">
           <div class="centered"style="background-color:black;opacity:70%">ALLESTISCI IL TUO ACQUARIO<p style="background-color:black;opacity:70%">Componi la vasca con legni, rocce e piante acquatiche</p>
           </div>
        </div>
        <div class="carousel-controls">
          <label for="img-1" class="prev-slide">
            <span>&lsaquo;</span>
          </label>
          <label for="img-3" class="next-slide">
            <span>&rsaquo;</span>
          </label>
        </div>
      </li>
     
      

      <input type="checkbox" name="radio-buttons" id="img-3" checked/>
      <li class="slide-container">
        <div class="mySlides fade">
          <img src="img/ring3.webp" style="width:100%">
           <div class="centered"style="background-color:black;opacity:70%">CURA IL TUO ACQUARIO<p style="background-color:black;opacity:70%">Scegli i prodotti ideali per la cura e la manutenzione del tuo acquario</p></div>
        </div>
        <div class="carousel-controls">
          <label for="img-2" class="prev-slide">
            <span>&lsaquo;</span>
          </label>
          <label for="img-1" class="next-slide">
            <span>&rsaquo;</span>
          </label>
        </div>
      </li>
      
      
    </ul>
    
  </div>
  
        <div style="text-align:center">
  			<span class="dot"></span> 
  			<span class="dot"></span> 
  			<span class="dot"></span> 
</div>

</div>
	
	
	<br>
	<div class="grid-container">
		<%
		if (products != null && products.size() != 0) {
			Iterator<?> it = products.iterator();
			while (it.hasNext()) {
				ArticoloBean bean = (ArticoloBean) it.next();
				LinkedList<FotoBean> foto = (LinkedList<FotoBean>) fotodao.getPhotos(bean);
		%>
	
		<div class="grid-item">
			<div class="thumbnail">
				<a href="Articolo?action=read&id=<%=bean.getId()%>">
					<img src="data:image/jpg;base64,<%=foto.get(0).getBase64img()%>" width=100% height=300 />
				</a>
			</div>
			<div class="item-description">
				<h4><%=bean.getNome()%></h4>
				<h5><%=String.format("%.2f", bean.getPrezzo())%> &euro;</h5>
				<h6><a href="Articolo?action=addC&id=<%=bean.getId()%>">Aggiungi al carrello</a></h6>
			</div>
		</div>

		<%
			}
		} else {
		%>
		<h3>Nessun prodotto disponibile</h3>
		<%
		}
		%>
	</div>

<div class="google-maps">
<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2938.959488196516!2d14.79335890661423!3d40.771155797250074!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133bc5a83bd547bb%3A0xd5ead49bc28f1e2e!2sVia%20Roma%2C%2084084%20Fisciano%20SA!5e0!3m2!1sit!2sit!4v1656688995724!5m2!1sit!2sit" frameborder="10" style="border:30" allowfullscreen></iframe>
</div>

<script>
let slideIndex = 0;
showSlides();

function showSlides() {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  let dots = document.getElementsByClassName("dot");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  slideIndex++;
  if (slideIndex > slides.length) {slideIndex = 1}    
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
  setTimeout(showSlides, 4000); // Change image every 2 seconds
}
</script>

</body>
	<%@ include file="./fragments/footer.jsp"%>
</html>


