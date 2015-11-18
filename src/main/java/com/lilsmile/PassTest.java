package com.lilsmile;

import db.*;
import db.entity.Answer;
import db.entity.Question;
import db.entity.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Smile on 04.11.15.
 */
@Path("/tests")
public class PassTest implements Constants{

    DBControllerMethods dbController = new DBContorller();



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTests() //do not need token
    {
        ArrayList<Test> tests = dbController.getTests();
        JSONArray jsonArray = new JSONArray();
        for (Test test : tests)
        {
            jsonArray.add(testToJSON(test));
        }
        StaticThings.writeInfo("Send all tests");//log
        return jsonArray.toString();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON) //do not need token
    public String getTestById(@QueryParam("id") String idString)
    {
        StaticThings.writeInfo("Send test #"+idString);//log
        return testToJSON(dbController.getTestById(Integer.valueOf(idString).intValue())).toString();
    }

    @POST
    @Path("/passed_test")
    @Consumes(MediaType.APPLICATION_JSON)
    public String passedTest(String body)
    {
        JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
        String token = (String) jsonObject.get(TOKEN);

        if (!StaticThings.checkToken(token))
        {
            return WRONG_TOKEN;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i<token.length(); i+=2)
        {
            sb.append(token.charAt(i));
        }
        String login = sb.toString();
        //todo : add adding to db
        StaticThings.writeInfo("get body:\n"+body);//log
        Mail mail = new Mail();
        mail.sendEmail(body);
        return OK;
    }

    @POST
    @Path("/created_test")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createdTest(String body)
    {
        //todo : add adding to db
        JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
        String token = (String) jsonObject.get(TOKEN);
        if (!StaticThings.checkToken(token))
        {
            StaticThings.writeInfo("got new test with wrong token");
            return WRONG_TOKEN;
        }

        try {
            dbController.addTest(jsonToTest((JSONObject) JSONValue.parse(body)));
            StaticThings.writeInfo("get test all right:\n" + body);//log
        } catch (Exception e)
        {
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<stackTraceElements.length; i++)
            {
                sb.append(stackTraceElements[i].toString()+"\n");
            }
            StaticThings.writeInfo(sb.toString());//log
            return SMTH_IS_WRONG;
        }
        return OK;
    }




    private JSONObject testToJSON(Test test)
    {
        JSONObject currentTest = new JSONObject();
        currentTest.put(TEST_ID,test.getIdTest());
        currentTest.put(TEST_CATEGORY,test.getTestCategory());
        currentTest.put(TITLE,test.getTitle());
        currentTest.put(AUTHOR,test.getAuthor());
        Date date = test.getDate();
        int year = date.getYear()+1900;
        int month = date.getMonth();
        int day = date.getDay();
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-");
        if (month<10)
        {
            sb.append("0");
        }
        sb.append(month).append("-");
        if (day<10)
        {
            sb.append(0);
        }
        sb.append(day);
        currentTest.put(DATE,sb.toString());
        currentTest.put(DESCRIPTION,test.getDescription());
        ArrayList<Question> questions = test.getQuestions();
        JSONArray jsonArrayQuestions = new JSONArray();
        for (Question question : questions)
        {
            JSONObject currentQuestion = new JSONObject();
            currentQuestion.put(NUMBER, question.getNumber());
            currentQuestion.put(TITLE, question.getTitle());
            ArrayList<Answer> answers = question.getAnswers();
            JSONArray jsonArrayAnswers = new JSONArray();
            for (Answer answer : answers)
            {
                JSONObject currentAnswer = new JSONObject();
                currentAnswer.put(NUMBER, answer.getNumber());
                currentAnswer.put(TITLE, answer.getTitle());
                currentAnswer.put(WEIGHT, answer.getWeight());
                jsonArrayAnswers.add(currentAnswer);
            }
            currentQuestion.put(ANSWERS, jsonArrayAnswers);
            jsonArrayQuestions.add(currentQuestion);
        }
        currentTest.put(QUESTIONS, jsonArrayQuestions);
        return currentTest;
    }

    private Test jsonToTest(JSONObject jsonTest)
    {
        String title = (String) jsonTest.get(TITLE);
        String description = (String) jsonTest.get(DESCRIPTION);
        JSONArray questionArr = (JSONArray) jsonTest.get(QUESTIONS);
        ArrayList<Question> questions = new ArrayList<Question>();
        for (int i = 0; i<questionArr.size(); i++)
        {
            JSONObject currentQuestionJSON = (JSONObject) questionArr.get(i);
            String questionTitle = (String) currentQuestionJSON.get(TITLE);
            int type = Integer.valueOf((String) currentQuestionJSON.get(TYPE));
            int number = Integer.valueOf((String) currentQuestionJSON.get(NUMBER));
            JSONArray answersJSON = (JSONArray) currentQuestionJSON.get(ANSWERS_ARR);
            ArrayList<Answer> answers = new ArrayList<Answer>();
            for (int j = 0; j<answersJSON.size(); j++)
            {
                JSONObject currentAnswerJSON = (JSONObject) answersJSON.get(i);
                String answerTitle = (String) currentAnswerJSON.get(TITLE);
                int weight = Integer.valueOf((String) currentAnswerJSON.get(WEIGHT));
                Answer currentAnswer = new Answer(j,answerTitle,Integer.valueOf(weight).intValue());
                answers.add(currentAnswer);
            }
            Question currentQuestion = new Question(i,questionTitle,answers, Integer.valueOf(type).intValue());
            questions.add(currentQuestion);
        }
        Test test = new Test(100,"author","category",title,description, new Date(System.currentTimeMillis()), questions);
        return test;
    }



}
