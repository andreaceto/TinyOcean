<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="./fragments/headerLogged.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>il mio account</title>
	<link href="./style/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
	LinkedList<String> indirizzi = (LinkedList<String>) session.getAttribute("indirizzi");
	ArrayList<String> metodiPagamento = (ArrayList<String>) session.getAttribute("metodi");
	UtenteBean user= (UtenteBean) session.getAttribute("currentSessionUser");
	%>
	<br>

	<div class="user-container">
		<form action="UserUpdate">
			<ul>
				<li>
					<div class="user">
						<h3>I miei dati</h3>
						<div class="user-label">Nome:</div>
							<input class="user" name=nome type="text" value=<%=user.getNome()%>>
					</div>
				</li>
				<li>
					<div class="user">
						<div class="user-label">Cognome:</div>
						<input class="user" name=cognome type="text" value=<%=user.getCognome()%>>
					</div>
				</li>
				<li>
					<div class="user">
						<div class="user-label">Email:</div>
						<input class="user" name=email  type="email" value=<%=user.getEmail()%>>
					</div>
				</li>
				<li>
					<div class="user">
						<div class="user-label">Data di nascita:</div>
						<input class="user" name=data type="date" value=<%=user.getDataNascita()%>>
					</div>
				</li>
				<li>
					<div class="user">
						<div class="user-label"></div>
						<input type="hidden" name=action value=update>
						<input type="submit" value=Salva>
					</div>
				</li>
			</ul>
		</form>
	</div>	
	<br>
	<div class="user-container">
		<ul>
			<li>
				<div class="user-payment">
				<h3>I miei indirizzi </h3>
				<select id="indirizzo" name="indirizzo" class=select-large>
				<%
				if (indirizzi != null) {
					for (String indirizzo : indirizzi) {
				%>
						<option value="<%=indirizzo%>"><%=indirizzo%></option>
				<%
					}
				} else {
				%>
					<option>Nessun indirizzo in archivio</option>
				<%
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
	<br>
	<div class="user-container">
		<ul>
			<li>
				<div class="user-payment">
					<h3>I miei metodi di pagamento</h3>
					<select id="metodoPagamento" name="pagamento" class=select-large>
					<%
					if (metodiPagamento != null) {
						for (String metodoPagamento : metodiPagamento) {
					%>
							<option value="<%=metodoPagamento%>"><%=metodoPagamento%></option>
					<%
						}
					} else {
					%>
						<option>Nessun metodo di pagamento registrato</option>
					<%
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
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>