<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.tinyOcean.model.*"%>
<%@page import="java.util.*"%>

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

<%
OrdineBean order = (OrdineBean) session.getAttribute("ordine");
LinkedList<ContenutoBean> products = (LinkedList<ContenutoBean>) session.getAttribute("products");
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
	if (order != null) {
	%>

	<div class=details>
		<table>
			<thead class=details>
				<tr>
					<th>Numero ordine</th>
					<th>Costo totale</th>
					<th>Data ordine</th>
					<th>Data di spedizione</th>
					<th>Indirizzo di spedizione</th>

				</tr>
			</thead>

			<tbody class=details>
				<tr>
					<td><%=order.getNumOrdine()%></td>
					<td><%=String.format("%.2f", order.getCostoTot())%>&euro;</td>
					<td><%=order.getDataOrdine()%></td>
					<td><%=order.getDataSpedizione()%></td>
					<td><%=order.getIndirizzoSped()%></td>
				</tr>
			</tbody>
		</table>
		<br>
	</div>
	<table>
		<thead class=details>
			<tr>
				<th>Nome</th>
				<th>Prezzo</th>
				<th>Iva</th>
				<th>Quantita acquistata</th>
			</tr>
		</thead>
		<tbody class=catalogo>
			<%
			if (products != null && products.size() != 0) {
				for (ContenutoBean bean : products) {
			%>
					<tr>
						<td><%=bean.getNomeArt()%></td>
						<td><%=String.format("%.2f", bean.getPrezzoAcq())%>&euro;</td>
						<td><%=bean.getIva()%>%</td>
						<td><%=bean.getQuantita()%></td>
					</tr>

			<%
				}
			} else {
			%>
				<tr>
					<td colspan="6">Nessun prodotto registrato</td>
				</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<%
	}
	%>

</body>
	<%@ include file="./fragments/footer.jsp"%>
</html>