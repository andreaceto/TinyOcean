<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.tinyOcean.model.OrdineBean"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Archivio ordini</title>
</head>
<body>
		
	<h2>Ordini effettuati</h2>
				
	<%
	List<OrdineBean> ordini = new LinkedList<OrdineBean>();
	ordini = (LinkedList<OrdineBean>) session.getAttribute("ordini");
	%>
	<div class="details">
		<table>
			<thead class="details">
				<tr>
					<th>Numero ordine:</th>
					<th>Acquirente:</th>
					<th>Importo totale:</th>
					<th>Data:</th>
				</tr>
			</thead>
			<tbody class="details">
				 <%
				if (ordini != null) {
					for (OrdineBean ordine : ordini) {
				%>
						<tr>
							<td><a
								href="Ordine?action=mostradettagli&codice=<%=ordine.getNumOrdine()%>"><%=ordine.getNumOrdine()%></a>
							</td>
							<td><%=ordine.getUtente()%></td>
							<td><%=ordine.getCostoTot()%> &euro;</td>
							<td><%=ordine.getDataOrdine()%> </td>
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
	<div class="date_input">
		<form id= selector action=OrderArchive>
			<INPUT type="date" name="startdate"> <INPUT type="date"
				name="enddate"> <INPUT type="submit" value="cerca">
		</form>
	</div>
	
	<div>
		<form action=OrderArchive>
			<INPUT type="text" name="username"> <INPUT type="submit"
				value="cerca">
		</form>
		
	</div>
	
</body>
</html>