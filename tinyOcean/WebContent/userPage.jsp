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

	<div class="user">
		<form action="UserUpdate">
					
						<h2>I miei dati</h2>
						
						
					<label>Nome:
				<input class="user" name=nome type="text" value=<%=user.getNome()%>>
			</label>
			<br>
						<label>Cognome:
						<input class="user" name=cognome type="text" value=<%=user.getCognome()%>>
						</label>
						<br>
				
						<label>Email:
						<input class="user" name=email  type="email" value=<%=user.getEmail()%>>
						</label>
						<br>

					
						<label>Data di nascita:
						<input class="user" name=data type="date" value=<%=user.getDataNascita()%>>
						</label>
						<br>

					
						<div class="user-label"></div>
						<input type="hidden" name=action value=update>
						<input class=btn type="submit" value=Salva>
					
		</form>
	</div>	
	<br>
	<div class="user">
		<ul>
			<li>
				<div class="user-payment">
				<h2>I miei indirizzi </h2>
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
					
				<button class="btn">
                 <a href="addressRegistration.jsp" style="text-decoration: none; color:white;">Inserisci un nuovo indirizzo</a>
                </button>
				</div>
				<br>
				</div>
			</li>
		</ul>
	</div>
	<br>
	<div class="user">
		<ul>
			<li>
				<div class="user-payment">
				
					<h2>I miei metodi di pagamento</h2>
	
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
				<button class="btn">
                 <a href="paymentMethodRegistration.jsp" style="text-decoration: none; color:white;">Inserisci un nuovo metodo di pagamento</a>
                </button>
					</div>
					<br>
				</div>
			</li>
		</ul>
	</div>
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>