let valid = true;
function validateform() {
	var via = $("#addressform input[name=via]").val();
	var numCivico = $("#addressform input[name=numCivico]").val();
	var cap = $("#addressform input[name=cap]").val();
	var provincia = $("#addressform input[name=provincia]").val();
	var citta = $("#addressform input[name=citta ]").val();
	street_validation(via);
	civic_number_validation(numCivico);
	zip_validation(cap);
	province_validation(provincia);
	city_validation(citta);
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
	if (civic_number.match(/^[0-9]{1,3}$/)) {
		return true;
	}
	$("#addressform input[name=numCivico]").css({
		"border-color": "red",
		"border-width": "thick"
	});
	$("#addressform input[name=numCivico]").val("");
	$("#addressform input[name=numCivico]").attr("placeholder", "numero civico non valido");
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
