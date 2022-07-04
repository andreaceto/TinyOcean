<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.tinyOcean.model.ArticoloBean, it.tinyOcean.model.RecensioneBean, it.tinyOcean.model.RecensioneDAO, it.tinyOcean.model.FotoDAO, it.tinyOcean.model.FotoBean"%>

<% 
if (session == null || session.getAttribute("currentSessionUser") == null || ((UtenteBean) session.getAttribute("currentSessionUser")).getNome() == "guest"){ 
%>
	<%@include file="./fragments/header.jsp"%>
<% 
} else{ 
%>
	<%@ include file="./fragments/headerLogged.jsp"%>
<% 
} 
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href = "./style/productDetails.css" rel = "stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<%
	ArticoloBean product = (ArticoloBean) request.getAttribute("product");
	RecensioneDAO recensioneDao = new RecensioneDAO();
	ArrayList<RecensioneBean> elencoRecensioni = recensioneDao.getRecensioniByProduct(product);
	FotoDAO fotodao = new FotoDAO();
	LinkedList<FotoBean> foto = (LinkedList<FotoBean>) fotodao.getPhotos(product);
	%>
	<title><%= product.getNome()%></title>
</head>
<body>

	<p class = "product-title-nascosto"><%= product.getProduttore() + " " + product.getNome() %></p>
	      <div class = "container">
	      
            <div class ="item">
             	<img class="image" src="data:image/jpg;base64,<%= foto.get(0).getBase64img()  %>">
            </div>
            
            <div class="item-descrizione">
                <p class = "product-title"><%= product.getProduttore() + " " + product.getNome() %></p>
                <p class = "descrizione"><%= product.getDescrizione()%></p>
                <p class = "product-title">&euro; <%= String.format("%.02f", product.getPrezzo()) %> <span style="font-size:0.85rem;">iva inc.</span></p>
                <p class = "product-title-nascosto">&euro; <%= String.format("%.02f", product.getPrezzo()) %> <span style="font-size:0.85rem;">iva inc.</span></p>
                <p class = "container-bottone">
                
                	<button class="aggiungi"><a href="Articolo?action=addC&id=<%=product.getId()%>">Aggiungi al carrello</a></button>
                
                </p>
            </div>
        </div>

		<br><br>
		
		
        <div class="recensioni" id="parte-recensione" style="display:none">
	        <div class="intestazione">
	        	<span class="recensionii-header">Lascia una recensione</span>
	        </div>
        	<form action="Recensione" method="get" class="form-recensione">
        		<input type="hidden" name="articolo" value="<%=product.getId()%>">
         
        		<!-- DIV VOTO -->
        		<div class="rate">
        			<input type="radio" id="star5" name="voto" value="5" required />
        			<label for="star5" title="text">5 stars</label>
        			<input type="radio" id="star4" name="voto" value="4" required/>
        			<label for="star4" title="text">4 stars</label>
        			<input type="radio" id="star3" name="voto" value="3" required/>
        			<label for="star3" title="text">3 stars</label>
        			<input type="radio" id="star2" name="voto" value="2" required/>
        			<label for="star2" title="text">2 stars</label>
        			<input type="radio" id="star1" name="voto" value="1" required/>
        			<label for="star1" title="text">1 star</label>
    			</div>
        		<!-- DOV -->
        		<br><br>
        		<textarea rows="5" cols="60" name="commento" placeholder=" inserisci una recensione..." id="rex"></textarea>
        		<input type="hidden" name="action" value="recensisci">
        		<br><br>
        		<div style="text-align:center">
        			<input type="submit" value="Recensisci" id="tasto-recensione">
        		</div>
        	</form>
        </div>
        
        
		<div class="intestazione">        
        	<span class="recensioni-header">Recensioni</span>
        	<br>
        </div>
        <section id="testimonials">
		<%for(RecensioneBean r : elencoRecensioni) { %>
		    <!--testimonials-box-container------>
		    <div class="testimonial-box-container">
		        <!--BOX-1-------------->
		        <div class="testimonial-box">
		            <!--top------------------------->
		            <div class="box-top">
		                <!--profile----->
		                <div class="profile">
		                    <!--img---->
		                    <div class="profile-img">
		                    
		                        <img src="https://cdn4.iconfinder.com/data/icons/sea-life-23/64/yellow_tang_fish_ocean_sea_sea_life-512.png" />
		                    </div>
		                    <!--name-and-username-->
		                    <div class="name-user">
		                        <span class="nomeUtente" style="color: #2f2f2f; font-size: 1.2rem;"><%= r.getUtente().getNome() %></span>
		                    </div>
		                </div>
		                <!--reviews------>
		                <div class="reviews">
		                    <span class="votoMedio"><%= r.getVoto() %></span>
		                    <!--Empty star-->
		                </div>
		            </div>
		            <!--Comments---------------------------------------->
		            <div class="client-comment">
		                <p><%= r.getCommento() %></p>
		            </div>
		        </div>
		    </div>
		<%} %>
		</section>
	    <script>
	    $(document).ready(function () {
        	//se il prodotto è stato acquistato mostra il pulsante recensisci
        	//se l'user non ha acquistato non mostra il pulsante
        	function ControllaRecensione(product) {
				return $.ajax({
					url : "Recensione",
					type : 'GET',
					async : false,
					cache : false,
					timeout : 30000,
					dataType : "json",
					data : {
						action : "check",
						id : product
					},
					success : function(data) {
						return data
					},
					fail : function(msg) {
						return true;
					}
				});
			}
		        
		        var res = ControllaRecensione(<%=product.getId()%>);
				if (res.responseJSON.message == "recensibile") {
					$("#parte-recensione").show();
				} else{	
					$('#rex').attr("placeholder", "  PUOI RECENSIRE SOLO SE HAI ACQUISTATO IL PRODOTTO")
				}
			
			

        });//chiusura JQUERY
        </script>
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>