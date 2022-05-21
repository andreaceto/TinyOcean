<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>Storico Ordini</title>
</head>
<body>
	<h2>I tuoi ordini</h2>
	<%
	List<OrdineBean> ordini = new LinkedList<OrdineBean>();
	ordini = (List<OrdineBean>) session.getAttribute("ordini");
	%>
	<div class="details">
		<table>
			<thead class=details>
				<tr>
					<th>Codice ordine:</th>
					<th>Data:</th>
					<th>Importo totale:</th>
				</tr>
			</thead>
			<tbody class=details>
				
				<%
				if (ordini != null) {
					for (OrdineBean ordine : ordini) {
				%>
			
						<tr>
							<td><a href="Ordine?action=mostradettagli&codice=<%=ordine.getNumOrdine()%>"><%=ordine.getNumOrdine()%></a></td>
							<td><%=ordine.getDataOrdine()%></td>
							<td><%=String.format("%.2f", ordine.getCostoTot())%>&euro;</td>
						</tr>
					<%
					}
					%>
			</tbody>
		</table>
	</div>
	<%
	} else {
	%>
	<h2>Nessun ordine registrato</h2>
	<%
	}
	%>

</body>
	<%@ include file="./fragments/footer.jsp"%>
</html>