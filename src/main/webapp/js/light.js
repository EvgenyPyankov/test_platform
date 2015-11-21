/*Basic functionality scripts */

var jsTest;

function loadTests() {

	$.ajax({
		dataType: "json",
		url: "rest/tests",
		data: {"token": window.authToken},
		success: function (json) {
			$("#testsTable tbody").append(printTests(json));
			$("#testsTable").trigger("update");
		}
	});
}

function printTests(arg) {
	var output = '';
	for (var i in arg) {
		output += '<tr><td>' + '<a href="pass_test.html?id=' + arg[i].test_id + '">' + arg[i].title + '</a></td><td>' + arg[i].author + '</td><td>' + arg[i].test_category + '</td></tr>';
	}
	return output;
}

function loadQuestions() {
	$.ajax({
		dataType: "json",
		url: "rest/tests/test?id=" + getParamValue('id'),
		data: {"token": window.authToken},
		success: function (json) {
			jsTest = json;
			$("#questionsPanel").append(printQuestions(json));
			printDescription(json);
		}
	});
}

function printQuestions(arg) {
	var output = '';
	for (var i in arg.questions) {
		output += '<div class="panel panel-default"><div class="panel-heading">Question ' + arg.questions[i].number + ': ' + arg.questions[i].title + ' </div><div class="panel-body">';
		for (var n in arg.questions[i].answers) {
			output += '<div class="radio"><label><input type="radio" name="optradio' + arg.questions[i].number + '" value=' + arg.questions[i].answers[n].number + ' /> ' + arg.questions[i].answers[n].title + '</label></div>';
		}
		output += '</div></div>';
	}
	return output;
	//TODO: consider remaking with $createNode function (if there is one)
}

function printDescription(arg) {
	$("#testTitle").html(arg.title);
	$("#testDesc").html(arg.description);
}

function sendTestAnswers() {
	if (checkRadios()) {
		data = populateData();
		$.ajax({
			url: 'rest/tests/passed_test',
			type: 'POST',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			headers: {"TOKEN": window.authToken},
			success: function (response) {
				//TODO: Get something in response and show something
				alert("Results have been sent. Press OK to return to tests list");
				window.location.href = 'choose_test.html';
			}
		});
	}
}

function populateData() {
	var answersTest = new Object();
	answersTest.test_id = jsTest.test_id;
	answersTest.token = window.authToken;
	answersTest.questions = [];
	var radioName = "";
	for (var i = 0; i < $(".panel-body").length; i++) {
		answersTest.questions[i] = new Object();
		radioName = "input:radio[name=optradio" + (i + 1) + "]:checked";
		answersTest.questions[i].number = (i + 1);
		answersTest.questions[i].answer = $(radioName).val();
	}
	return answersTest;
}

function checkRadios() {
	for (var i = 1; i < $(".panel-body").length+1; i++) {//todo check it
		if (!$("input[name='optradio" + i + "']:checked").val()) {
			alert("Please, answer question number " + i);
			return false;
		}
	}
	return true;
}

function getParamValue(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	}
	else {
		return results[1] || 0;
	}
}

function validateEmail() {
	var regexp = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/i;
	if (regexp.test($('#inputEmail').val())) {
		return true;
	}
	else {
		return false;
	}
}

function validateLogin() {
	var regx = /^[a-zA-Z0-9_]+$/i;
	if (regx.test($('#inputEmail').val()) || validateEmail()) {
		$("#emailHelp").html("");
		$('#emailVal').removeClass("has-error");
		return true;
	}
	else {
		$("#emailHelp").html("Enter a valid login or email!");
		$('#emailVal').addClass("has-error");
		return false;
	}
}

function validatePassword() {
	var regx = /^[a-zA-Z0-9_]+$/i;
	if ($('#inputPassword').val().length != 0 && regx.test($('#inputPassword').val())) {
		$("#passHelp").html("");
		$('#passVal').removeClass("has-error");
		return true;
	}
	else {
		$("#passHelp").html("Enter a password!");
		$('#passVal').addClass("has-error");
		return false;
	}
}

function validateReg() {
	var regx = /^[a-zA-Z0-9_]+$/i;
	var regexp = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/i;

	if (!regx.test($('#inputUsernameReg').val())) {
		$('#unregVal').addClass("has-error");
		return false;
	} else {
		$('#unregVal').removeClass("has-error")
	}
	if (!regexp.test($('#inputEmailReg').val())) {
		$('#emailregVal').addClass("has-error");
		return false;
	} else {
		$('#emailregVal').removeClass("has-error")
	}
	if (!regx.test($('#inputPasswordReg').val()) || !regx.test($('#inputPasswordRegRepeat').val()) || !($('#inputPasswordReg').val() == $('#inputPasswordRegRepeat').val())) {
		$('#passregVal').addClass("has-error");
		return false;
	} else {
		$('#passregVal').removeClass("has-error")
	}
	return true;
}

function auth(username, hash) {
	$.ajax({
		url: 'rest/auth/login',
		type: "POST",
		data: JSON.stringify({"userName": username, "passHash": md5(hash)}),
		dataType: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (response) {
			if (response.result == 255) {
				$("#emailHelp").html("Wrong login or email!");
				$('#emailVal').addClass("has-error");
			} else if (typeof response.token != 'undefined') {
				document.cookie = "authToken=" + response.token;
				window.location.href = 'menu.html';
			}
		}
	});
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') c = c.substring(1);
		if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
	}
}

function init() {
	var token = getCookie("authToken");
	if (token != undefined)
		window.authToken = token;
	if (window.authToken == undefined)
		window.location.href = 'index.html';
}

function logIn() {
	if (validateLogin() && validatePassword()) {
		auth($("#inputEmail").val(), $("#inputPassword").val());
	}
}

function logInGuest() {
	$.ajax({
		url: 'rest/auth/anonymous',
		type: "POST",
		data: JSON.stringify({"userName": "anonymous"}),
		dataType: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (response) {
			document.cookie = "authToken=" + response.token;
			window.location.href = 'menu.html';
		}
	});
}

function signUp() {
	if (validateReg()) {
		$.ajax({
			url: 'rest/auth/signup',
			type: "POST",
			data: JSON.stringify({
				"userName": $("#inputUsernameReg").val(),
				"passHash": md5($("#inputPasswordReg").val()),
				"email": $("#inputEmailReg").val()
			}),
			dataType: "json",
			contentType: 'application/json; charset=utf-8',
			success: function (response) {
				if (response.result == 205) {
					$('#unregVal').addClass("has-error");
					$("#regErrors").html("Username already in use!");
				} else if (response.result == 210) {
					$("#regErrors").html("Email already in use!");
					$('#emailregVal').addClass("has-error");
				} else if (response.result == 200) {
					$("#regErrors").html("Registration complete!");
				}
			}
		});
	}
}

function logOut() {
	$.ajax({
		url: 'rest/auth/logout',
		type: "POST",
		data: JSON.stringify({"token": window.authToken}),
		dataType: "json",
		contentType: 'application/json; charset=utf-8',
		success: function () {
			document.cookie = 'authToken=; Max-Age=0';
			window.location.href = 'index.html';
		}
	});
}

