<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*" import = "it.tinyOcean.model.*"%>
<%@page isErrorPage="true" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Errore 403</title>
		<link rel="stylesheet" type="text/css" href="./style/errors.css">	
	</head>
	
<body>
<% UtenteBean utente = (UtenteBean) request.getAttribute("utente"); %>

	<div>	
	<br><br>
		<h1 class="error-code">403</h1>
		<p class="error">Accesso negato... non hai i permessi per accedere.</p>	
		
			<form method="get" action="./Homepage.jsp">
		 <input type="submit" value="Home"/>
	</form>
		
	</div>
	
		

</body>
</html>