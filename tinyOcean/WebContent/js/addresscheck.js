/**
 * 
 */
let valid = true;
function validateform() {
	var via = $("#addressform input[name=via]").val();
	var numero_civico = $("#addressform input[name=numero_civico]").val();
	var cap = $("#addressform input[name=cap]").val();
	var città = $("#addressform input[name=citta ]").val();
	var provincia = $("#addressform input[name=provincia]").val();
	street_validation(via);
	civic_number_validation(numero_civico);
	zip_validation(cap);
	city_validation(città);
	province_validation(provincia);
	return valid;
}

function street_validation(street) {
	if (!street.match(/[!@$%^&*(),?":{}|<>]/g)) {
		return true;
	}
	$("#addressform input[name=via]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	$("#addressform input[name=via]").val("");
	$("#addressform input[name=via]").attr("placeholder", "indirizzo non valido");

	valid = false;
	return false;
}

function civic_number_validation(civic_number) {
	var match = /d{1,4}[a-zA-Z]?/
	if (civic_number.match(match)) {
		return true;
	}
	$("#addressform input[name=numero_civico]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	$("#addressform input[name=numero_civico]").val("");
	$("#addressform input[name=via]").attr("placeholder", "numero civico non valido");
	valid = false;
	return false;
}

function zip_validation(zipcode) {
	var numbers = /^[0-9]+$/;
	if (zipcode.match(numbers)) {
		return true;
	}
	$("#addressform input[name=cap]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	$("#addressform input[name=cap]").val("");
	$("#addressform input[name=cap]").attr("placeholder", "cap non valido");
	valid = false;
	return false;
}

function city_validation(city) {
	var letters = /^[a-zA-Z]+$/;
	if (city.match(letters)) {
		return true;
	}
	$("#addressform input[name=citta]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	valid = false;
	return false;
}

function province_validation(province) {
	var letters = /^[a-zA-Z]+$/;
	if (province.match(province)) {
		return true;
	}
	$("#addressform input[name=provincia]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	valid = false;
	return false;
}


$(document).ready(function(event) {

	console.log("ready!");

	$("#addressform").submit(function(event) {


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
