package db.DAOImpl;

import com.lilsmile.StaticThings;
import db.entity.*;
import db.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TestDAOImpl implements db.DAO.TestDAO {
    public void addTest(Test test) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(test);
        session.getTransaction().commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public Test getTestById(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Test test =  (Test) session.get(Test.class, id);
        if (session.isOpen()) session.close();
        return test;
    }

    public List<Test> getTests() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Test> tests = null;
        tests = session.createCriteria(Test.class).list();
        if (session.isOpen()) session.close();
        StaticThings.writeInfo("yeyeyeyeyey"+ tests.size()+"   "+ tests.get(0).getTitle());
        return tests;
    }

    public void addPassedTest(Test test, User user) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UserPass userPass = new UserPass();
        userPass.setTest(test);
        userPass.setUser(user);
        userPass.setDate(new Date());
        session.save(userPass);
        UserAnswer userAnswer;
        for (Question question:test.getQuestions()){
            if (question.getQuestionType()==3){
                userAnswer = new UserAnswer();
                userAnswer.setQuestion(question);
                question.getUserAnswers().add(userAnswer);
                userAnswer.setAnswer(null);
                userAnswer.setText(question.getAnswerText());
                userAnswer.setUserPass(userPass);
                userPass.getUserAnswers().add(userAnswer);
                session.save(userAnswer);
            }
            else {
                for (Answer answer:question.getAnswers()){
                    if (answer.getIsChoosed()==1) {
                         userAnswer = new UserAnswer();
                        userAnswer.setQuestion(question);
                        question.getUserAnswers().add(userAnswer);
                        userAnswer.setAnswer(answer);
                        answer.getUserAnswers().add(userAnswer);
                        userAnswer.setUserPass(userPass);
                        userPass.getUserAnswers().add(userAnswer);
                        session.save(userAnswer);
                    }
                }
            }
        }

        session.getTransaction().commit();

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public Test getPassedTestByUser(Test test, User user) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserPass> results= null;

        String hql = String.format("FROM UserPass E WHERE E.test = %d AND E.user=%d",test.getIdTest(),user.getIdUser());
        Query query = session.createQuery(hql);
        results = query.list();

        UserPass up = results.get(0);
        for (Question question:test.getQuestions()){
            for (Answer answer:question.getAnswers()){
                if (question.getQuestionType()==3){
                    Query q =session.createQuery("FROM UserAnswer E WHERE E.userPass=:user_pass AND E.question=:_question");
                    q.setParameter("user_pass",up);
                    q.setParameter("_question",question);
                    List<UserAnswer> userAnswers = q.list();
                    UserAnswer userAnswer = userAnswers.get(0);
                    question.setAnswerText(userAnswer.getText());
                }
                else{
                    Query q = session.createQuery(String.format("FROM UserAnswer E WHERE E.userPass=%d AND E.answer=%d",up.getId(),answer.getIdAnswer()));
                    List<UserAnswer> ua = q.list();
                    if (ua.size()>0) answer.setIsChoosed(1);
                }
            }
        }


        if (session != null && session.isOpen()) {
            session.close();
        }
        if (results!=null) return test;
        return null;
    }

    public List<UserPass> getPassedTestsByUser(User user) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM UserPass E WHERE E.user = :id_user";
        Query query = session.createQuery(hql);
        query.setParameter("id_user",user);
        List<UserPass> userPasses = null;
        userPasses = query.list();
        return userPasses;
    }
}
