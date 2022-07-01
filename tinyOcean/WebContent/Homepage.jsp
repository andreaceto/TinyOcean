<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.tinyOcean.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.awt.image.*"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="javax.xml.*"%>


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

<%
Collection<?> products = (Collection<?>) request.getAttribute("products");
FotoDAO fotodao = new FotoDAO();
if (products == null) {
	response.sendRedirect("./Articolo");
	return;
}
%>

<!DOCTYPE html>
<html>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./style/style.css" rel="stylesheet" type="text/css">
	<script src="./js/photo.js"></script>
<title>Tiny Ocean</title>
</head>
<body>
	<h2>Prodotti</h2>
	<br>
	<div class="grid-container">
		<%
		if (products != null && products.size() != 0) {
			Iterator<?> it = products.iterator();
			while (it.hasNext()) {
				ArticoloBean bean = (ArticoloBean) it.next();
				LinkedList<FotoBean> foto = (LinkedList<FotoBean>) fotodao.getPhotos(bean);
		%>
	
		<div class="grid-item">
			<div class="thumbnail">
				<a href="Articolo?action=read&id=<%=bean.getId()%>">
					<img src="data:image/jpg;base64,<%=foto.get(0).getBase64img()%>" width=100% height=300 />
				</a>
			</div>
			<div class="item-description">
				<h4><%=bean.getNome()%></h4>
				<h5><%=String.format("%.2f", bean.getPrezzo())%> &euro;</h5>
				<h6><a href="Articolo?action=addC&id=<%=bean.getId()%>">Aggiungi al carrello</a></h6>
			</div>
		</div>

		<%
			}
		} else {
		%>
		<h3>Nessun prodotto disponibile</h3>
		<%
		}
		%>
	</div>

</body>
	<%@ include file="./fragments/footer.jsp"%>
</html>
