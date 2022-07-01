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
	<title>Pagina di registrazione</title>
	<link href="./style/style.css" rel="stylesheet" type="text/css">
	<link href="./style/error.css" rel="stylesheet" type="text/css">
	<script src="./js/registration.js"></script>
</head>
<body>
	<h2>Compila il form per registrarti</h2>
	<div class=register>
	<div class="alert">
			<span class="closebtn"
				onclick="this.parentElement.style.display='none';">&times;</span>
				<span id="errorspan"></span>
		</div>
		<form action="UserServlet" id="myform" method="get">
			<label>Username
				<input class=register type=text name="usr" placeholder="username" required>
			</label>
			<br>
			<label>Nome
				<input class=register type=text name="nome" placeholder="mario" required>
			</label>
			<br>
			<label>Cognome
				<input class=register type=text name="cogn" placeholder="rossi" required>
			</label>
			<br>
			<label>Email
				<input class=register type=email name=email placeholder="someone@something.com">
			</label>
			<br>
			<label>Password
				<input class=register type=password name=pwd placeholder="supersecret" required>
			</label>
			<br>
			<label>Numero di telefono
				<input class=register type=number name=num_tel required>
			</label>
			<br>
			<label>Paese di residenza
				<input class=register type=text name=paese placeholder="Italia" required>
			</label>
			<br>
			<label>Data di nascita
				<input class=register type=date name=data placeholder="AAAA-MM-DD" required>
			</label>
			<br>
			<input class=btn type=submit value=Registrati>
		</form>
	</div>
</body>
	<%@ include file="./fragments/footer.jsp" %>
</html>