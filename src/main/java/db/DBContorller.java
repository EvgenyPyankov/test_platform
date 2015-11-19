package db;

import db.entity.*;
import db.hibernate.Factory;

import java.sql.SQLException;
import java.util.*;

public class DBContorller implements DBControllerMethods {
    public Test getTestById(int id) throws SQLException {
        return Factory.getInstance().getTestDAO().getTestById(id);
    }

    public void addTest(Test test) throws SQLException{
        for(Question question:test.getQuestions()){
            Set<Answer> answers = new HashSet<Answer>();
            deepCopy(question.getAnswers(),answers);
            question.setAnswers(answers);
            for (Answer answer:question.getAnswers()){
                answer.setQuestion(question);
            }
            question.setTest(test);
        }

        Factory.getInstance().getTestDAO().addTest(test);
    }

    public void addPassedTest(Test test, User user) throws SQLException {
        Factory.getInstance().getTestDAO().addPassedTest(test, user);
    }

    public void addUser(User user) throws SQLException{
        Factory.getInstance().getUserDAO().addUser(user);
    }

    public User getUserByLogin(String login) throws SQLException{
        return Factory.getInstance().getUserDAO().getUserByLogin(login);
    }

    public User getUserByEmail(String email)throws SQLException {
        return Factory.getInstance().getUserDAO().getUserByEmail(email);
    }

    public User getUserById(int id) throws SQLException {
        return Factory.getInstance().getUserDAO().getUserById(id);
    }

    public List<Test> getTests()throws SQLException{
        return Factory.getInstance().getTestDAO().getTests();
    }

    public Test getPassedTest(Test test, User user) throws SQLException {
        return Factory.getInstance().getTestDAO().getPassedTestByUser(test, user);
    }

    public List<UserPass> getPassedTestsByUser(User user) throws SQLException {
        return Factory.getInstance().getTestDAO().getPassedTestsByUser(user);
    }

    private void deepCopy(Set<Answer> from, Set<Answer>to){
        for(Answer answer:from)
            to.add(new Answer(answer.getNumber(),answer.getTitle()));
    }
}
