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
				<a href="Homepage.jsp">Tiny Ocean</a>
			</div>
			<nav>
				<div class="nav-mobile">
					<a id="nav-toggle" href="#!"><span></span></a>
				</div>
				<ul class="nav-list">		
					<li class="nav-right"><a href="#!">Ciao, Accedi</a>
					<ul class="nav-dropdown">
							<li><a href="loginPage.jsp">Accedi</a></li>
							<li><a href="./UserPage">Il mio account</a></li>
							<li><a href="./OrdineStoria">I miei ordini</a></li>
					</ul></li>		
					<li><a href="./OrdineStoria">Storico ordini</a></li>
					<li><a href="cart.jsp">Carrello</a></li>
				</ul>
			</nav>
		</div>
	</section>
</body>
</html>