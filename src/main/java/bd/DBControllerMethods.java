package bd;

import java.util.ArrayList;

public interface DBControllerMethods {
    //put methods you need here
    Test getTestById(int id);
    ArrayList<Test> getTests();
    public void addUser(User user);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);


}
