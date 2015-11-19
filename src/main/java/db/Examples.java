package db;

import db.entity.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Examples {
    static DBContorller dbContorller = new DBContorller();

    static void addUser(){
        User user = new User("John","john@mail.ru",777);
        try {
            dbContorller.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addTest(){
        User user = null;
        try {
            user = dbContorller.getUserByLogin("John");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Test test;
        Set<Question> questions = new HashSet<Question>();
        Set<Answer> answers = new HashSet<Answer>();

        answers.add(new Answer(1,"ja"));
        answers.add(new Answer(2,"nein"));
        answers.add(new Answer(3,"weiss ich nicht"));

        questions.add(new Question(1,"Who?",answers,1));
        questions.add(new Question(2,"blablab?",answers,1));

        test = new Test("Test by John", TestCategory.MATH,questions, user);

        try {
            dbContorller.addTest(test);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
