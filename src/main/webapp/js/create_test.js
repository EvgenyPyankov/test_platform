var numberOfQuestionsOnThePage = 1;

function chooseQuestionType(numberOfQuestion, arg)
{
	var question = document.getElementById('question'+numberOfQuestion);
	alert(question.innerHTML);
}


function addNewQuestion(element)
{
	numberOfQuestionsOnThePage++;
	var parent = element.parentNode;
	var question = document.getElementById(parent.id).cloneNode(true);
	question.id='question'+numberOfQuestionsOnThePage;
	var form = document.getElementById('questions');
	parent.parentNode.insertBefore(question, parent.nextSibling);

	var questions = document.getElementsByClassName('question');
	for (var i = 0; i< questions.length; i++)
	{
		questions[i].id='question'+(i+1);
	}

	var questionTypes = document.getElementsByName("question-type");
	var numberOfAnswers = document.getElementsByName('number-of-answer');
	var n = parent.id.substring(parent.id.length-1)-1;
	var type = questionTypes[n].value;
	var number = numberOfAnswers[n].value;

	var selects = question.getElementsByTagName('select');
	for (var i = 0; i<selects.length; i++)
	{
		if (selects[i].name == 'question-type')
		{
			selects[i].value = type;
		}
		if (selects[i].name == 'number-of-answer')
		{
			selects[i].value = number;
		}
	}
}

function choosedNumberOfAnswers(element)
{
	var value = element.value;
	var currentQuestion = element.parentNode.parentNode;
	var collectionAnswersDiv = currentQuestion.getElementsByClassName('question-answers');
	var answersDiv=collectionAnswersDiv[0];
	var type;
	while(answersDiv.firstChild)
	{
		var radioOrCheckbox = answersDiv.firstChild.firstChild;
		if (radioOrCheckbox.type=='radio')
		{
			type='radio';
		} else
		{
			type='checkbox';
		}
		answersDiv.removeChild(answersDiv.firstChild);
	}
	for (var i = 0; i<value; i++)
	{
		var newP = document.createElement('p');
		newP.innerHTML='<input type="'+type+'" name="'+(i+1)+'" disabled> <input type="text" name="answer'+(i+1)+'">';
		answersDiv.appendChild(newP);
	}
}

function choosedType(element)
{
	var value = element.value;
	var currentQuestion = element.parentNode.parentNode;
	var collectionAnswersDiv = currentQuestion.getElementsByClassName('question-answers');
	var answersDiv=collectionAnswersDiv[0];
	var collectionNumberOfAnswers = document.getElementsByName('number-of-answer');
	var n = currentQuestion.id.substring(currentQuestion.id.length-1);
	var numberOfAnswers = collectionNumberOfAnswers[n-1];
	var count = numberOfAnswers.value;
	while(answersDiv.firstChild)
	{
		console.log(answersDiv.firstChild);
		answersDiv.removeChild(answersDiv.firstChild);
	}
	if (value==1)
	{
		numberOfAnswers.disabled=false;
		for (var i = 0; i<count; i++)
		{
			var newP = document.createElement('p');
			newP.innerHTML='<input type="radio" name="'+(i+1)+'" disabled> <input type="text" name="answer'+(i+1)+'">';
			answersDiv.appendChild(newP);
		}
	} else if (value==2)
	{
		numberOfAnswers.disabled=false;
		for (var i = 0; i<count; i++)
		{
			var newP = document.createElement('p');
			newP.innerHTML='<input type="checkbox" name="'+(i+1)+'" disabled> <input type="text" name="answer'+(i+1)+'">';
			answersDiv.appendChild(newP);
		}
	} else 
	{
		numberOfAnswers.disabled=true;
		var newP = document.createElement('p');
		newP.innerHTML='<input type="text" name="answer'+(i+1)+'">';
		answersDiv.appendChild(newP);
	}
}

function submitForm()
{
	$('#form').submit(function(e)
	{
		e.preventDefault();
		var m_method=$(this).attr('method');
		var m_action=$(this).attr('action');
		var m_data=$(this).serialize();
		alert(m_data);
		$.ajax({
			type: m_method,
			url: m_action,
			data: m_data,
			success: function(){
				window.location.href = 'choose_test.html';
			}
		});
	});
}
