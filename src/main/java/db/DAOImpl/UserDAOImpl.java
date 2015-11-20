package db.DAOImpl;

import com.lilsmile.StaticThings;
import db.DAO.UserDAO;
import db.entity.User;
import db.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    public void addUser(User user) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        if (session != null && session.isOpen()) {
            session.close();
        }

    }

    public User getUserByEmail(String email) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> results = null;

        String hql = String.format("FROM User E WHERE E.email = '%s'", email);
        Query query = session.createQuery(hql);
        results = query.list();
        if (results != null && results.size()>0) return results.get(0);

        if (session != null && session.isOpen()) {
            session.close();
        }

        return null;
    }

    public User getUserByLogin(String login) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> results = null;


        String hql = String.format("FROM User E WHERE E.login = '%s'", login);
        Query query = session.createQuery(hql);
        results = query.list();


        if (session != null && session.isOpen()) {
            session.close();
        }
        if (results != null && results.size()>0) return results.get(0);
        return null;
    }

    public User getUserById(int id) throws SQLException {
            Session session = HibernateUtil.getSessionFactory().openSession();
            User user =  (User) session.get(User.class, id);
            if (session.isOpen()) session.close();
            return user;
    }
}
