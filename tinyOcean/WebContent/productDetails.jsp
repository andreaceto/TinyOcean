<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.tinyOcean.model.ArticoloBean"%>
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

<%ArticoloBean product = (ArticoloBean) request.getAttribute("product");%>
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
	<%
	}
	%>
	
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>