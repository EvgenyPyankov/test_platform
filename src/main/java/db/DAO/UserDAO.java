package db.DAO;

import db.entity.User;

import java.sql.SQLException;

public interface UserDAO {
    void addUser(User user) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    User getUserByLogin(String login) throws SQLException;

    User getUserById(int id) throws SQLException;

}
