package db.hibernate;

import db.DAO.UserDAO;
import db.DAO.TestDAO;
import db.DAOImpl.TestDAOImpl;
import db.DAOImpl.UserDAOImpl;

public class Factory {
    private static TestDAO testDAO = null;
    private static UserDAO userDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public TestDAO getTestDAO(){
        if (testDAO == null){
            testDAO = new TestDAOImpl();
        }
        return testDAO;
    }

    public UserDAO getUserDAO(){
        if (userDAO == null){
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}
