<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.tomcat.jni.Address"%>
<%@page import="it.tinyOcean.model.*"%>
<%@page import="java.util.*"%>

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

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="./style/style.css" rel="stylesheet" type="text/css">
	<link href="./style/error.css" rel="stylesheet" type="text/css">
	<script src="./js/checkout.js"></script>
	<title>Checkout</title>
</head>
<body>
	<div class="alert">
		<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
		<span id="errorspan"></span>
	</div>
	<%
	Cart cart = (Cart) session.getAttribute("cart");
	String itemID = request.getParameter("itemID");
	if (itemID != null) {
		String numItemsString = request.getParameter("numItems");
		if (numItemsString != null) {
			int numItems;
			try {
				numItems = Integer.parseInt(numItemsString);
			} catch (NumberFormatException nfe) {
				numItems = 1;
			}
			cart.setNumOrdered(Integer.parseInt(itemID), numItems);
		}
	}
	%>

	<%
	if (cart != null) {

		LinkedList<String> indirizzi = (LinkedList<String>) session.getAttribute("indirizzi");
		ArrayList<String> metodiPagamento = (ArrayList<String>) session.getAttribute("metodi");
	%>
	<h1>Riepilogo dell'ordine</h1>
	<div>
		<div class="user-container">
			<ul>
				<li>
					<div class="user-payment">
						<h3>Indirizzo di consegna</h3>
						<select id="indirizzo" name="indirizzo" class=select-large>
							<%
							if (!indirizzi.isEmpty() ) {
								for (String indirizzo : indirizzi) {
									%>
									<option value="<%=indirizzo%>"><%=indirizzo%></option>
							<%
								}
							}
							%>
							
						</select>
						<br>
						<div class="user-link">
							<a href="addressRegistration.jsp">Inserisci un nuovo indirizzo</a>
						</div>
						<br>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<br>
	<div>
		<div class="user-container">
			<ul>
				<li>
					<div class="user-payment">
						<h3>Metodo di pagamento</h3>
						<select id="metodoPagamento" name="pagamento" class=select-large>
							<%
							if (!metodiPagamento.isEmpty() ) {
								for (String metodoPagamento : metodiPagamento) {
							%>
									<option value="<%=metodoPagamento%>"><%=metodoPagamento%></option>
							<%
								}
							}
							%>
						</select>
						<br>
						<div class="user-link">
							<a href="paymentMethodRegistration.jsp">Inserisci un nuovo metodo di pagamento</a>
						</div>
						<br>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<h3>Rivedi gli articoli</h3>
	<div class=details>
		<table >
			<thead class=details>
				<tr>
					<th>Articolo:</th>
					<th>Quantit&agrave;</th>
					<th>Prezzo:</th>
					<th>Azioni:</th>
				</tr>
			</thead>
			<%
			List<ItemOrder> prodcart = cart.getProducts();
			float prezzo_finale = 0;
			for (ItemOrder beancart : prodcart) {
			%>
				<tbody class=details>
					<tr>
						<td><%=beancart.getNome()%></td>
						<td><%=beancart.getNumItems()%></td>
						<td><%=String.format("%.2f", beancart.getTotalCost())%>&euro;</td>
						<td><a href="Articolo?action=deleteC&id=<%=beancart.getId()%>">Elimina dal carrello</a></td>
					</tr>
				</tbody>
			<%
				prezzo_finale += beancart.getTotalCost();
			}
			%>
			<tfoot class=cart>
				<tr>
					<th colspan=3>Totale:</th>
					<td><%=String.format("%.2f", prezzo_finale)%> &euro;</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<br>
	<form>
		<input type=hidden name=action value=CompletaOrdine>
		<div class=wrapper>
			<button id="concludi" class=btn type="submit" formaction="Ordine">Concludi ordine</button>
		</div>
	</form>
	<%
	}
	%>

</body>
	<%@ include file="./fragments/footer.jsp"%>
</html>