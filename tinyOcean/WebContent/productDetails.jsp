<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.tinyOcean.model.ArticoloBean, it.tinyOcean.model.RecensioneBean"%>
<% 
if (session == null || session.getAttribute("currentSessionUser") == null){ 
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
ArticoloBean product = (ArticoloBean) request.getAttribute("product");
List<RecensioneBean> elencoRecensioni = (List<RecensioneBean>) request.getAttribute("recensioni");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dettagli</title>
	<link href="./style/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h2>Dettagli</h2>
	<%
	if (product != null) {
	%>
		<div class=details>
			<table>
				<thead class=details>
					<tr>
						<th>Produttore</th>
						<th>Nome</th>
						<th>Prezzo</th>
						<th>Descrizione</th>
						<th>Peso</th>
						<th>Altezza</th>
						<th>Larghezza</th>
						<th>Lunghezza</th>
						<th>Saldo</th>
					</tr>
				</thead>
				<tbody class=details>
					<tr>
						<td><%=product.getProduttore()%></td>
						<td><%=product.getNome()%></td>
						<td><%=String.format("%.2f", product.getPrezzo())   %> &euro;</td>
						<td><%=product.getDescrizione()%></td>
						<td><%=product.getPeso()%></td>
						<td><%=product.getAltezza()%></td>
						<td><%=product.getLarghezza()%></td>
						<td><%=product.getLunghezza()%></td>
						<td><%=product.getSaldo()%></td>
					</tr>
				</tbody>
			</table>
			<br>
			<div class="wrapper">
				<form class=details action="Articolo?action=addC&id=<%=product.getId()%>" method="post" id="checkout">
					<button class=btn type="submit">Aggiungi al carrello</button>
				</form>
			</div>
		</div>
		
        <div class="recensioni" id="parte-recensione" style="display:none">
        	<form action="RecensioneServlet" method="get" class="form-recensione">
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
        	<span class="prodotti-consigliati-header">Recensioni</span>
        	<br>
        </div>
        <%}%>
        <section id="testimonials">
		<%for(RecensioneBean r : elencoRecensioni) {%>
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
		                    
		                        <img src="https://cdn3.iconfinder.com/data/icons/avatars-15/64/_Ninja-2-512.png" />
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
		<%}%>
		</section>
	    <script>
         //aggiungere  
        </script>
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>