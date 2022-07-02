let valid = true;

function validateform() {
	var tipo = $("#paymentform input[name=tipo]:checked").val();
	var numCarta = $("#paymentform input[name=numCarta]").val();
	var scadenza = $("input[name=scadenza]").val();
	var titolare = $("#paymentform input[name=titolare]").val();
	type_validation(tipo);
	number_validation(numCarta, tipo);
	data_validation(scadenza);
	owner_validation(titolare);
	return valid;
}

function type_validation(type) {
	if (type) {
		console.log(type);
		return true;
	}
	console.log(type);
	var oldtext = $('#errorspan').text();
	$('#errorspan').text(oldtext.concat("selezionare un tipo di metodo di pagamento \n"));
	$(".alert").css({ "display": "block" });
	valid = false;
	return false;
}

function owner_validation(owner) {
	var match = /(^[A-Za-z]{3,16})([ ]{0,1})([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})$/;
	if (owner.match(match)) {
		return true;
	}
	$("#paymentform input[name=titolare]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	$("#paymentform input[name=titolare]").val("");
	$("#paymentform input[name=titolare").attr("placeholder", "solo caratteri alfabetici");
	valid = false;
	return false;
}

function number_validation(number, type) {
	var numbers = /^[0-9]{16}$|^[0-9]{13}$/;
	var alphanumbers = /^[a-zA-Z0-9]{27}$/;
	if (type === "carta di credito") {
		if (number.match(numbers)) {
			return true;
		}
		$("#paymentform input[name=numCarta]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#paymentform input[name=numCarta]").val("");
		$("#paymentform input[name=numCarta]").attr("placeholder", "il numero deve essere composto da 13 o 16 numeri");
		valid = false;
		return false;
	}
	else if (type === "conto bancario") {
		if (number.match(alphanumbers)) {
			return true;
		}
		$("#paymentform input[name=numCarta]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#paymentform input[name=numCarta]").val("");
		$("#paymentform input[name=numCarta]").attr("placeholder", "l'IBAN deve essere composto da 27 caratteri alfanumerici");
		valid = false;
		return false;

	}
}

function data_validation(data) {
	const today = new Date()
	const date = new Date(data);
	if (date > today) {
		return true
	}
	var oldtext = $('#errorspan').text();
	$('#errorspan').text(oldtext.concat("la data di scadenza non può essere precedente ad oggi \n"));
	$(".alert").css({ "display": "block" });
	valid = false;
	return false;
}


$(document).ready(function(event) {

	console.log("ready!");

	$("#paymentform").submit(function(event) {
		$('#errorspan').text("");

		$(":input").removeAttr("style");

		if (validateform()) {
			console.log("metodo di pagamento valido");
		}
		else {
			valid=true;
			console.log("metodo di pagamento non valido");
			event.preventDefault;
			event.stopPropagation();
			return false;
		}
	})

	$(":input").click(function() {
		this.focus();

	})

}
)
