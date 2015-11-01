/*Basic functionality scripts */


function loadTests() {
	$.getJSON("jsons/testslist.json", function (json) {
		$("#testsTable tbody").append(printTests(json));
		$("#testsTable").trigger("update");
		//$("#testsTable:last-child").append(printTests(json)); //Still don't know what's the best way
	});
}

function printTests(arg) {
	var output = '';
	for (var i in arg.tests) {
		output += '<tr><td>' + arg.tests[i].title + '</td><td>' + arg.tests[i].author + '</td><td>' + arg.tests[i].rating + '</td></tr>';
	}
	return output;
}

function loadQuestions() {
	$.getJSON("jsons/MathTest.json", function (json) {
		$("#questionsPanel").append(printQuestions(json));
	});
}

function printQuestions(arg) {
	var output = '';
	for (var i in arg.questions) {
		output += '<div class="panel panel-default"><div class="panel-heading">Question ' + arg.questions[i].number + ': ' + arg.questions[i].text + ' </div><div class="panel-body">';
		$.each(arg.questions[i].answers, function () {
			$.each(this, function (key, value) {
				output += '<div class="radio"><label><input type="radio" name="optradio' + arg.questions[i].number + '"/> ' + value + '</label></div>';
			})
		});
		output += '</div></div>';
	}
	return output;
}