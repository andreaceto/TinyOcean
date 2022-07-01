/**
 * 
 */
let valid = true;
function validateform() {
	var username = $("#loginform input[name=un]").val();
	var password = $("#loginform input[name=pw]").val();
	username_validation(username, 5, 12);
	password_validation(password, 7, 12);
	return valid;
}

function username_validation(username, minlenght, maxlenght) {
	var uid_len = username.length;
	console.log(uid_len);
	if (uid_len == 0 || uid_len > maxlenght || uid_len < minlenght) {
		$("#loginform input[name=un]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#loginform input[name=un]").val("");
		$("#loginform input[name=un]").attr("placeholder", "l'username deve essere tra i 5 e i 15 caratteri");

		valid = false;
		return false;
	}
	return true;
}

function password_validation(passid, minlenght, maxlenght) {
	var passid_len = passid.length;
	if (passid_len == 0 || passid_len < minlenght || passid_len > maxlenght) {
		$("#loginform input[name=pw]").css({
			"border-color": "red",
			"border-width": "thick"
		});
		$("#loginform input[name=pw]").val("");
		$("#loginform input[name=pw]").attr("placeholder", "la password deve essere tra 7 e 12 caratteri");

		valid = false;
		return false;

	}
	return true;
}

$(document).ready(function() {



	$("#loginform button[name=login]").click(function(event) {

		
		$(":input").removeAttr("style");

		if (validateform()) {

			console.log(" credenziali well-formed");
		}
		else {
		
			console.log("credenziali non  well-formed");
			event.preventDefault;
			event.stopPropagation();
			valid=true;
			return false;
		}
	});

	$(":input").click(function() {
		this.focus();

	});
}
)




