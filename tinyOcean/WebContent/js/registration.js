
let valid = true;
function validateform() {
	var username = $("#myform input[name=usr]").val();
	var nome = $("input[name=nome]").val();
	var cognome = $("input[name=cogn]").val();
	var email = $("input[name=email]").val();
	var password = $("input[name=pwd]").val();
	var num_tel = $("input[name=num_tel]").val();
	var paese = $("input[name=paese]").val();
	var data = $("input[name=data]").val();

	username_validation(username, 5, 12);
	name_validation(nome);
	surname_validation(cognome);
	ValidateEmail(email);
	password_validation(password, 7, 12);
	num_tel_validation(num_tel);
	country_validation(paese);
	data_validation(data);

	return valid;
}


function username_validation(username, minlenght, maxlenght) {
	var uid_len = username.length;
	console.log(uid_len);
	if (uid_len == 0 || uid_len > maxlenght || uid_len < minlenght) {
		$("#myform input[name=usr]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=usr]").val("");
		$("#myform input[name=usr]").attr("placeholder", "l'username deve essere tra i 5 e i 15 caratteri");

		valid = false;
		return false;
	}
	return true;
}

function name_validation(name) {
	var letters = /^[A-Za-z]+$/;
	if (name.match(letters)) {
		console.log("nome valido");
		return true;
	}
	else {
		$("#myform input[name=nome]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=nome]").val("");
		$("#myform input[name=nome]").attr("placeholder", "il nome può contenere solo lettere");
		valid = false;
		return false;

	}

}

function surname_validation(surname) {
	var letters = /^[A-Za-z]+$/;
	if (surname.match(letters)) {
		return true;
	}
	else {
		$("#myform input[name=cogn]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=cogn]").val("");
		$("#myform input[name=cogn]").attr("placeholder", "il cognome può contenere solo lettere");

		valid = false;
		return false;
	}

}

function ValidateEmail(uemail) {
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if (uemail.match(mailformat)) {
		return true;
	}
	else {
		$("#myform input[name=email]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=email]").val("");
		$("#myform input[name=email]").attr("placeholder", "email non valida");

		valid = false;
		return false;
	}
}

function password_validation(passid, minlenght, maxlenght) {
	var passid_len = passid.length;
	if (passid_len == 0 || passid_len < minlenght || passid_len > maxlenght) {
		$("#myform input[name=pwd]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=pwd]").val("");
		$("#myform input[name=pwd]").attr("placeholder", "la password deve essere tra 7 e 12 caratteri");

		valid = false;
		return false;

	}
	return true;
}

function num_tel_validation(num_tel) {
	var phoneno = /^\d{10}$/;
	if (num_tel.match(phoneno)) {
		return true;
	}
	else {
		$("#myform input[name=num_tel]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=num_tel]").val("");
		$("#myform input[name=num_tel]").attr("placeholder", "numero di telefono non valido");

		valid = false;
		return false;
	}
}

function country_validation(paese) {
	var letters = /^[A-Za-z]+$/;
	if (paese.match(letters)) {
		return true;
	}
	else {
		$("#myform input[name=paese]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#myform input[name=paese]").val("");
		$("#myform input[name=paese]").attr("placeholder", "il paese può contenere solo lettere");

		valid = false;
		return false;
	}
}

function data_validation(data) {
	const today = new Date()
	const date = new Date(data);
	if (date < today) {
		return true
	}

	$('#errorspan').text("la data di nascita non può essere successiva ad oggi \n");
	$(".alert").css({ "display": "block" });
	valid = false;
	return false;
}


$(document).ready(function(event) {

	console.log("ready!");

	$("#myform").submit(function(event) {
		$('#errorspan').text("");

		$(":input").removeAttr("style");

		if (validateform()) {
			
			console.log("iscrizione valida");
		}
		else {

			console.log("iscrizione non valida");
			event.preventDefault;
			event.stopPropagation();
			valid = true;
			return false;
		}
	})

	$("#myform input[name=usr]").keyup(function(event) {
		$.post("./validation", { "username": $("#myform input[name=usr]").val() }, function(data) {
			console.log(data);
			if (data===true) {
				$("#myform input[name=usr]").css({
					"border-color": "red",
					"border-width": "thick"
				});
				console.log("username già esistente");

			}
			else {
				$("#myform input[name=usr]").css({
					"border-color": "green",
					"border-width": "thick"
				});
				console.log("username nuovo");
			}
		}, 'JSON');
	});


	$(":input").click(function() {
		this.focus();

	})


}
)







