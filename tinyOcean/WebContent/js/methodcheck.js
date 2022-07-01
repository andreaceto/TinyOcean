/**
 * 
 */
/**
 * 
 */

let valid = true;
function validateform() {
	var tipo = $("#paymentform input[name=tipo]:checked").val();
	var titolare = $("#paymentform input[name=titolare]").val();
	var numero = $("#paymentform input[name=numero]").val();
	var data = $("input[name=scadenza]").val();
	type_validation(tipo);
	owner_validation(titolare);
	number_validation(numero, tipo);
	data_validation(data);
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
	var match = /^[A-Za-z]+$/
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
		$("#paymentform input[name=numero]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#paymentform input[name=numero]").val("");
		$("#paymentform input[name=numero]").attr("placeholder", "il numero deve essere composto da 13 o 16 numeri");
		valid = false;
		return false;
	}
	else if (type === "conto bancario") {
		if (number.match(alphanumbers)) {
			return true;
		}
		$("#paymentform input[name=numero]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#paymentform input[name=numero]").val("");
		$("#paymentform input[name=numero]").attr("placeholder", "l'iban deve essere composto da 27 caratteri alfanumerici");
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
			console.log("indirizzo valido");
		}
		else {
			valid=true;
			console.log("indirizzo non valido");
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
