var currentQuestion;
currentQuestion.typeOfQuestion=1; //1 - one from many, 2 - many from many, 3 - text
currentQuestion.numberOfAnswers=4;

function chooseQuestionType(numberOfQuestion, arg)
{
	var question = document.getElementById('question'+numberOfQuestion);
	alert(question.innerHTML);
}


function addNewQuestion()
{
	var question = document.getElementById('question1').cloneNode(true);
	var form = document.getElementById('questions');
	form.appendChild(question);
}
