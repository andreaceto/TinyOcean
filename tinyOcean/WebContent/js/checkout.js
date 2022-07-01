/**
 * 
 */

let valid = true;
function validateform() {
	var address = $("#indirizzo").val();
	var method = $("#metodoPagamento").val();
	console.log(address);
	console.log(method);
	address_validation(address);
	method_validation(method);

	return valid;
}

function address_validation(address) {

	if (address) {
		return true;
	}

	var oldtext = $('#errorspan').text();
	$('#errorspan').text(oldtext + " \n "+"selezionare un indirizzo valido e se non presente inserirlo  \r\n" );
	$(".alert").css({"white-space": "pre-wrap"},{ "display": "inline" })

	valid = false;
	return false;

	function newFunction() {
		return "));";
	}
}

function method_validation(method) {

	if (method) {
		return true;
	}
	var oldtext = $('#errorspan').text();
	$('#errorspan').text(oldtext+" \n "+"selezionare un tipo di metodo di pagamento valido e se non presente inserirlo  \r\n");
	$(".alert").css({ "display": "block" });
	valid = false;
	return false;
}




$(document).ready(function(event) {


	$("button").click(function(event) {


$('#errorspan').text("");

		if (validateform()) {

			

		}
		else {

		 valid=true;
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
