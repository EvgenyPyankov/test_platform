/*Basic functionality scripts */


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
		output += '<tr><td>' + '<a href="mathtest.html?id=' + arg[i].test_id + '">' + arg[i].title + '</a></td><td>' + arg[i].author + '</td><td>' + arg[i].test_category + '</td></tr>';
	}
	return output;
}

function loadQuestions() {
	$.urlParam = function (name) {
		var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
		if (results == null) {
			return null;
		}
		else {
			return results[1] || 0;
		}
	}
	$.getJSON("rest/tests/test?id=" + $.urlParam('id'), function (json) {
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
	$.ajax({
		type: "POST",
		url: "rest/tests/passed_test",
		data: populateData(),
		dataType: "json"
	});
	alert("Results have been sent. Press OK to return to tests list");
	window.location.href = 'choose_test.html';
}
function populateData() {
	var questions = [];
	var n = 0;
	var radioName ="";
	$(".panel-body").each(function () {
		n++;
	});
	for (var i = 0; i < n; i++) {
		questions[i] = new Object();
		radioName="input:radio[name=optradio"+(i+1)+"]:checked";
		questions[i].number = (i + 1);
		questions[i].answer = $(radioName).val();
	}
	return JSON.stringify(questions);
}

