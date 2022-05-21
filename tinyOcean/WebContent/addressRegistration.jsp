<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	<title>Aggiungi indirizzo</title>
</head>
<body>
	<h2> Compila il form per inserire un nuovo indirizzo di consegna</h2>
	<div class=register>
		<form action="IndirizzoServlet" id="addressform">
			<label>Via<input class=address type=text name=via  placeholder="Via Armando Diaz" required></label>
			<br>
			<label>Numero Civico<input class=address type=number name=numCivico placeholder="15" required></label>
			<br>
			<label>Cap<input class=address type=number name=cap placeholder="84018" required></label>
			<br>
			<label>Provincia<input class=address type=text name=provincia placeholder="Salerno" required></label>
			<br>
			<label>Città<input class=address type=text name=citta  placeholder="Salerno" required></label>
			<br>
			<input class=btn type=submit value=Aggiungi>
		</form>
	</div>
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>