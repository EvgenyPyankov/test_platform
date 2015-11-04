package com.lilsmile;

import bd.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    public String getTests(@PathParam("id") String idString)
    {
        ArrayList<Test> tests = dbController.getTests();
        JSONArray jsonArray = new JSONArray();
        for (Test test : tests)
        {
            jsonArray.add(testToJson(test));
        }

        return jsonArray.toString();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTestById(@QueryParam("id") String idString)
    {

        return testToJson(dbController.getTestById(Integer.valueOf(idString).intValue())).toString();
    }

    @POST
    @Path("/passed_test")
    @Consumes(MediaType.TEXT_PLAIN)
    public String passedTest(String body)
    {
        Mail mail = new Mail();
        mail.sendEmail(body);
        return null;
    }



    private JSONObject testToJson(Test test)
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


}
