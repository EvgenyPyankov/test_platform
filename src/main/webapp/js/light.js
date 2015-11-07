/*Basic functionality scripts */

var jsTest;

function loadTests() {
	$.getJSON("rest/tests", function (json) {
		$("#testsTable tbody").append(printTests(json));
		$("#testsTable").trigger("update");
		//$("#testsTable:last-child").append(printTests(json)); //Still don't know what's the best way
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
	$.getJSON("rest/tests/test?id=" + getParamValue('id'), function (json) {
		jsTest = json;
		$("#questionsPanel").append(printQuestions(json));
		printDescription(json);
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
			async: false,
			success: function (response) {
			}
		});
		alert("Results have been sent. Press OK to return to tests list");
		window.location.href = 'choose_test.html';
	}
}

function populateData() {
	var answersTest = new Object();
	answersTest.test_id = jsTest.test_id;
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

function auth(username, hash) {
	$.ajax({
		url: 'rest/tests/passed_test',
		type: "POST",
		data: JSON.stringify({"userName": username, "passHash": hash}),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function () {
			window.location.href = 'choose_test.html';
		}
	});
}

function checkRadios() {
	for (var i = 1; i < $(".panel-body").length + 1; i++) {
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
	var regx = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/i;
	if (regx.test($('#inputEmail').val())) {
		$("#emailHelp").html("");
		return true;
	}
	else {
		$("#emailHelp").html("Enter a valid email!");
		return false;
	}
}

function validatePassword() {
	if ($('#inputPassword').val().length != 0) {
		return true;
	}
	else {
		$("#passHelp").html("Enter a password!");
		return false;
	}
}

function logIn() {
	if (validateEmail() && validatePassword()) {
		auth($("#inputEmail").val(), $("#inputPassword").val());
	}
}

function signUp() {

}

