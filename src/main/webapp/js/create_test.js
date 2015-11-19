var numberOfQuestionsOnThePage = 1;

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
	var weightOrNot = document.getElementsByName('weight-select');
	var n = parent.id.substring(parent.id.length-1)-1;
	var type = questionTypes[n].value;
	var number = numberOfAnswers[n].value;
	var weight = weightOrNot[n].value;


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
		if (selects[i].name == 'weight-select')
		{
			selects[i].value = weight;
		}
	}
}


function changeWeightFlag(element)
{
	var question = element.parentNode.parentNode;
	var spans = question.getElementsByClassName('weightSpan');
	for (var i = 0; i<spans.length; i++)
	{
		if (spans[i].style.visibility=="hidden"){
			spans[i].style.visibility="visible";
		} else{
			spans[i].style.visibility="hidden";
		}
	}

}

function choosedNumberOfAnswers(element)
{
	var currentQuestion = element.parentNode.parentNode;
	var collectionAnswersDiv = currentQuestion.getElementsByClassName('question-answers');
	var answersDiv=collectionAnswersDiv[0];
	var questionType=currentQuestion.getElementsByClassName('question-type')[0].value;
	var questionAnswers=currentQuestion.getElementsByClassName('number-of-answer')[0].value;
	var questionWeight=currentQuestion.getElementsByClassName('weight-or-not')[0].value;
	var type,number,weight;

	if (questionType==1)
	{
		type='radio';
	} else
	{
		type='checkbox';
	}

	number=questionAnswers;

	if (questionWeight==1)
	{
		weight='visibility: visible;';
	} else{
		weight='visibility: hidden;';
	}
	var currentAnswers = new Array();
	var count = 0;
	while(answersDiv.firstChild)
	{
		if (answersDiv.firstChild instanceof HTMLParagraphElement) {
			currentAnswers[count]=answersDiv.firstChild.querySelectorAll('input')[1].value;
			count++;
		}
		answersDiv.removeChild(answersDiv.firstChild);
	}
	for (var x = count; x<number; x++)
	{
		currentAnswers[x]="";
	}
	for (var i = 0; i<number; i++)
	{
		var newP = document.createElement('p');
		newP.innerHTML='<input type="'+type+'" name="type"> <input type="text" name="answer" value="'+currentAnswers[i]+'">'+'<span class="weightSpan" style="'+weight+'">Weight:<input type="text" name="weight" size="5"></span>';
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
	var weightOrNot = document.getElementsByName('weight-select')[n-1].value;
	var weightStyle;
	if (weightOrNot==1)
	{
		weightStyle='visibility: visible;';
	} else{
		weightStyle='visibility: hidden;';
	}
	var count = numberOfAnswers.value;
	var currentAnswers = new Array();
	var tmpN = 0;
	while(answersDiv.firstChild)
	{
		if (answersDiv.firstChild instanceof HTMLParagraphElement) {
			currentAnswers[tmpN]=answersDiv.firstChild.querySelectorAll('input')[1].value;
			console.log(currentAnswers[tmpN]);
			tmpN++;
		}
		answersDiv.removeChild(answersDiv.firstChild);
	}
	for (var x = tmpN; x<count; x++)
	{
		currentAnswers[x]="";
	}

	if (value==1)
	{
		numberOfAnswers.disabled=false;
		for (var i = 0; i<count; i++)
		{
			var newP = document.createElement('p');
			newP.innerHTML='<input type="radio" name="type"> <input type="text" name="answer" value="'+currentAnswers[i]+'">'+'<span class="weightSpan" style="'+weightStyle+'">Weight:<input type="text" name="weight" size="5"></span>';
			answersDiv.appendChild(newP);
		}
	} else if (value==2)
	{
		numberOfAnswers.disabled=false;
		for (var i = 0; i<count; i++)
		{
			var newP = document.createElement('p');
			newP.innerHTML='<input type="checkbox" name="type"> <input type="text" name="answer" value="'+currentAnswers[i]+'">'+'<span class="weightSpan" style="'+weightStyle+'">Weight:<input type="text" name="weight" size="5"></span>';
			answersDiv.appendChild(newP);
		}
	} else 
	{
		numberOfAnswers.disabled=true;
		var newP = document.createElement('p');
		newP.innerHTML='<input type="text" name="type" style="visibility:hidden;"><input type="text" name="answer" value="'+currentAnswers[0]+'">'+'<span class="weightSpan" style="'+weightStyle+'">Weight:<input type="text" name="weight" size="5"></span>';
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
		//var m_data=$(this).serialize();
		//m_data= JSON.stringify(m_data);

		var data = {
			token:window.authToken,
			title:document.getElementsByName('title')[0].value,
			description:document.getElementsByName('description')[0].value,
			questions:new Array()
			};

		var questions = document.getElementsByClassName('question');
		for (var i = 0; i<questions.length; i++)
		{
			var questionTitle = questions[i].getElementsByClassName('question-title')[0].querySelectorAll('input')[0].value;
			var questionType = questions[i].getElementsByClassName('question-type')[0].value;
			var numberOfAnswers = questions[i].getElementsByClassName('number-of-answer')[0].value;
			var weightOrNot = questions[i].getElementsByClassName('weight-or-not')[0].value;
			var answers = questions[i].getElementsByClassName('question-answers')[0].querySelectorAll('input');
			var stringAnswers = new Array();
			var stringAnswersNumber=0;
			function Answer (number, title, weight, right) {
				this.number = number;
				this.title = title;
				this.weight = weight;
				this.right = right;
			}
			// var oneAnswer = {
			// 		number:0,
			// 		title:'',
			// 		weight:0,
			// 		right:0
			// 	};

			var oneAnswer = new Answer(0,'',0,0);
			for (var j = 0; j<answers.length; j++)
			{
				if (answers[j].type=='checkbox')
				{
					if (answers[j].checked)
					{
						oneAnswer.right=1;
					}
					else
					{
						oneAnswer.right=0;
					}
				}
				if (answers[j].type=='radio')
				{
					if (answers[j].checked)
					{
						oneAnswer.right=1;
					}
					else
					{
						oneAnswer.right=0;
					}
				}
				if (answers[j].name=='answer')
				{
					oneAnswer.title=answers[j].value;
				}
				if (answers[j].name=='weight')
				{
					if (weightOrNot==1)
					{	
						oneAnswer.weight=answers[j].value;
					} else
					{
						oneAnswer.weight=0;
					}
					oneAnswer.number=stringAnswersNumber+1;
					stringAnswers[stringAnswersNumber]=new Answer(oneAnswer.number, oneAnswer.title, oneAnswer.weight, oneAnswer.right);
					stringAnswersNumber++;
				}
			}
			var damnCurentQuestion = {
				title:questionTitle,
				type:questionType,
				number:numberOfAnswers,
				answersArr:stringAnswers
			}

			data.questions[i]=damnCurentQuestion;
		}
		data = JSON.stringify(data);
		$.ajax({
			type: m_method,
			url: m_action,
			data: data,
			contentType: 'application/json; charset=utf-8',
			headers: {"TOKEN": window.authToken},
			async: false,
			success: function(){
				window.location.href = 'choose_test.html';
			}
		});
	});
}
