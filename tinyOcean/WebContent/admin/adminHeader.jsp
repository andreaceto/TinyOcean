<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.tinyOcean.model.UtenteBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://use.fontawesome.com/747292109b.js"></script>
	<link href="./style/headerStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<section class="navigation">
		<div class="nav-container">
			<div class="brand">
				<a href="Homepage.jsp"><span class="text-primary">T</span>iny<span class="text-secondary">O</span>cean</a>
			</div>
			
			<nav>
				<div class="nav-mobile">
					<a id="nav-toggle" href="#!"><span></span></a>
				</div>
				<ul class="nav-list">
					
						<li><a href="#">Ciao, <%=((UtenteBean) session.getAttribute("currentSessionUser")).getNome()%></a></li>
						<li><a href="./OrdineArchivio">Storico ordini</a></li>
						<li><a href="./Catalogo">Catalogo</a></li>
						<li><a href="./Logout">Esci</a></li>
				</ul>
			</nav>
		</div>
	</section>
</body>
</html>