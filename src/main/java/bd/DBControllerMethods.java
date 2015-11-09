package bd;

import java.util.ArrayList;

public interface DBControllerMethods {
    //put methods you need here

    //tests
    Test getTestById(int id);
    ArrayList<Test> getTests();
    void addTest(Test test);


    //users
    void addUser(User user);
    User getUserByLogin(String login);
    User getUserByEmail(String email);


}
