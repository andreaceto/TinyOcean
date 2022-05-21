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
	<title>Login</title>
</head>

	<%
	if(request.getParameter("login")!=null){
		if(request.getParameter("login").equals("wrong")){
	%>
		<h2 class=error>Le credenziali inserite sono sbagliate </h2>	
	<%
		}
	}
	%>

	<h2>Accedi / Registrati</h2>
	<h3 style="text-align: center">Hai già un account?</h3>
	
	<div class=login>
		<form action="Login" class=login id="loginform">
			<label><input class=login type="text" name="un" placeholder="Username"></label> <br><br>
			<label><input class=login type="password" name="pw" placeholder="Password" ></label><br><br>
			<button class=btn type="submit" name="login">Login</button>
			<button class=btn type="submit" formaction="registrationPage.jsp">Crea un account</button>
		</form>
	</div>

</body>

</html>

<%@ include file="./fragments/footer.jsp"%>