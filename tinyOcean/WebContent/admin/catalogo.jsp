<%@page import="it.tinyOcean.model.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="./adminHeader.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Catalogo</title>
	<link href="./style/style.css" rel="stylesheet" type="text/css">
	<script src="./js/catalogo.js"></script>
</head>
<body>
	<%
	Collection<?> products = (Collection<?>) session.getAttribute("products");
	%>
	<h2 style="text-align: center;">Inserisci nuovo articolo</h2>
	<div class=details>
		<form method="post" id="a_form" action="Articolo">
			<table>
				<thead class=details>
					<tr>
						<th>Id</th>
						<th>Produttore</th>
						<th>Nome</th>
						<th>Descrizione</th>
						<th>Prezzo</th>
						<th>Stock</th>
						<th>Peso</th>
						<th>Altezza</th>
						<th>Larghezza</th>
						<th>Lunghezza</th>
						<th>Saldo</th>
					</tr>
				</thead>
				<tbody  class=details>
	        		<tr>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="id" type="number" 
		            		maxlength="20" required placeholder="Inserisci ID">
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="produttore" type="text"
		            		maxlength="20" required placeholder="Inserisci produttore">
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="nome" type="text"
		            		maxlength="20" required placeholder="Inserisci nome">
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="descrizione" type="text" maxlength="100"  
		            		required placeholder="Inserisci descrizione">
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="prezzo" type="number" min="0" value="0" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="stock" type="number" min="1" value="1" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="peso" type="number" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="altezza" type="number" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="larghezza" type="number" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="lunghezza" type="number" required>
		        		</td>
		        		<td>
		            		<input type="hidden" name="action" value="insert">
		            		<input class=details name="saldo" type="number" value="0" required>
		        		</td>
		        		<td>
		        			<input class=btn-det type="submit" value="Aggiungi">
						</td>
						<td>
							<input class=btn-det type="reset" value="Ripristina">
						</td>
					</tr>
	        	</tbody>	
    		</table>
    	</form>
	</div>	

	<h2>Prodotti</h2>	 
	<div class=details>
		<table>
			<thead class=details>
				<tr>
					<th><a href="Catalogo?sort=id">Id </a></th>
					<th><a href="Catalogo?sort=produttore">Produttore</a></th>
					<th><a href="Catalogo?sort=nome">Nome</a></th>
					<th>Descrizione</th>
					<th><a href="Catalogo?sort=prezzo">Prezzo</a></th>
					<th><a href="Catalogo?sort=stock">Stock</a></th>
					<th>Peso</th>
					<th>Altezza</th>
					<th>Larghezza</th>
					<th>Lunghezza</th>
					<th>Saldo</th>
		    		<%
					if (products != null && products.size() != 0) {
						Iterator<?> it = products.iterator();
						while (it.hasNext()) {
							ArticoloBean bean = (ArticoloBean) it.next();
					%>
				</tr>
			</thead>
	        <tbody  class=details>
	        	<tr>
	        		<td>
	        			<INPUT class="catalogo-admin old" type=hidden  name="oldID" value=<%=bean.getId()%>> 
						<INPUT class="catalogo-admin new" type=number  name="newID" value=<%=bean.getId()%>> 
	        		</td>
	        		<td>
						<INPUT type=TEXT class="produttore" name=produttore value=<%=bean.getProduttore()%>>
					</td>
	        		<td>
						<INPUT type=TEXT class="nome" name=nome value=<%=bean.getNome()%>>
					</td>
					<td>
						<INPUT type=TEXT class="descrizione" name=descrizione value=<%=bean.getDescrizione()%>>
					</td>
					<td>
						<INPUT type=text step="any" class="prezzo" name=prezzo value=<%=String.format("%.2f", bean.getPrezzo())%>>
					</td>
					<td>
						<INPUT type=number min="0" class="stock" name=stock value=<%=bean.getStock()%>>
					</td>
					<td>
						<INPUT type=number step="any" class=peso name=peso value=<%=bean.getPeso()%>>
					</td>
					<td>
						<INPUT type=number step="any" class=altezza name=altezza value=<%=bean.getAltezza()%>>
					</td>
					<td>
						<INPUT type=number step="any" class=larghezza name=larghezza value=<%=bean.getLarghezza()%>>
					</td>
					<td>
						<INPUT type=number step="any" class=lunghezza name=lunghezza value=<%=bean.getLunghezza()%>>
					</td>
					<td>
						<INPUT type=number min="0" class="saldo" name=saldo value=<%=bean.getSaldo()%>>
					</td>	
					<td>
						<a href="Articolo?action=delete&id=<%=bean.getId()%>" style="text-decoration: none">Elimina</a>
					</td>	
	    		</tr>
	    		<%
					}
				} else {
				%>
				<tr>
					<td colspan="6">Nessun prodotto disponibile</td>
				</tr>
				<%
				}
				%>
			</tbody>   	
		</table>
	</div>
</body>
</html>