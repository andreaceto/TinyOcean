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
							<li><a href="./OrdineStoria">I miei ordini</a></li>
							<li><a href="./Logout">Esci</a></li>
						</ul>
					</li>
					<li><a href="./OrdineStoria">Storico ordini</a></li>
					<li><a href="./cart.jsp">Carrello</a></li>

					<%
					if (((UtenteBean) session.getAttribute("currentSessionUser")).isAdmin())
					{ 
					%>
					
						<li><a href="./OrdineArchivio">Storico ordini admin</a></li>
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