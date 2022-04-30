<%@page import="it.tinyOcean.model.UtenteBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
	<section class="navigation">
		<div class="nav-container">
			<div class="brand">
				<a href="./Homepage.jsp">Tiny Ocean</a>
			</div>
			
			<nav>
				<div class="nav-mobile">
					<a id="nav-toggle" href="#!"><span></span></a>
				</div>
				<ul class="nav-list">
					<li>
						<a href="#!">Ciao, <%=((UtenteBean) session.getAttribute("currentSessionUser")).getNome()%></a>
						<ul class="nav-dropdown">
							<li><a href="./UserPage">Il mio account</a></li>
							<li><a href="">I miei ordini</a></li> <!-- da implementare -->
							<li><a href="./Logout">Esci</a></li>
						</ul>
					</li>
					<li><a href="#">Storico ordini</a></li> <!-- da implementare -->
					<li><a href="#">Carrello</a></li> <!-- da implementare -->

					<%
					if (((UtenteBean) session.getAttribute("currentSessionUser")).isAdmin())
					{ 
					%>
					
						<li><a href="#">Storico ordini admin</a></li> <!-- da implementare -->
						<li><a href="./Catalogo">Catalogo</a></li>
					
					<%
					}
					%>

				</ul>
			</nav>
		</div>
	</section>
</body>
</html>