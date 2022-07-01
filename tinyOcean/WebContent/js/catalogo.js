console.log("ready");

$(document).ready(function(event) {
	console.log("ready");

	$(".btn-det").click(function(event) {
		var row = $(this).closest('tr');
		var oldID = row.find('.old').val();
		var newID = row.find('.new').val();
		var produttore = row.find('.produttore').val();
		var nome = row.find('.nome').val();
		var descrizione = row.find('.descrizione').val();
		var prezzo = row.find('.prezzo').val();
		var stock = row.find('.stock').val();
		var altezza = row.find('.altezza').val();
		var larghezza = row.find('.larghezza').val();
		var lunghezza = row.find('.lunghezza').val();
		var saldo = row.find('.saldo').val();
		
		console.log(newID);
		console.log(oldID);

		$.post("./CatalogoUpdate", {
			"newID": newID,
			"produttore": produttore,
			"nome": nome,
			"descrizione": descrizione,
			"prezzo": prezzo,
			"stock": stock, 
			"peso": peso,
			"altezza": altezza,
			"larghezza": larghezza,
			"lunghezza": lunghezza,
			"saldo": saldo,
			"Oldid": oldID
		});
	})
})
