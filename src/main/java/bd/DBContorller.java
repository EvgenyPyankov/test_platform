package bd;

import java.util.ArrayList;
import java.util.Date;

public class DBContorller implements DBControllerMethods{
    private ArrayList<Test> tests = new ArrayList<Test>();
    private ArrayList<User> users = new ArrayList<User>();
    public DBContorller(){
        createTests();
        createUsers();
    }

    public Test getTestById(int id){
       for (Test item:tests)
           if (item.getIdTest()==id)
               return item;
        System.err.println("Not found!");
        return null;
    }

    public ArrayList<Test> getTests(){
        return tests;
    }

    public void addUser(User user){
        users.add(user);
    }

    public User getUserByLogin(String login){
        for(User item:users)
            if (item.getLogin().equals(login))
                return item;
        return null;
    }

    public User getUserByEmail(String email){
        for (User item:users)
            if (item.getEmail().equals(email))
                return item;
        return null;
    }

    private void createUsers(){
        users.add(new User("kingOfTheWorld","king@gmail.com",777));
        users.add(new User("admin","admin@gmail.com",555));
    }

    private void createTests(){
        ArrayList<Question> questions;
        ArrayList<Answer> answers;

        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();

        answers.add(new Answer(1,"yes",1));
        answers.add(new Answer(2,"rather yes",0));
        answers.add(new Answer(3,"i don't know",0));
        answers.add(new Answer(4,"rather no",0));
        answers.add(new Answer(5,"no",1));

        questions.add(new Question(1,"I am able to relax", answers));
        questions.add(new Question(2,"I tend to focus on upsetting situations or events happening in my life", answers));
        questions.add(new Question(3,"I feel fearful for no reason", answers));
        questions.add(new Question(4,"I am as happy as the people around me", answers));
        questions.add(new Question(5,"I have diarrhea, constipation, or other digestive problems", answers));
        questions.add(new Question(6,"I have a dry mouth", answers));
        questions.add(new Question(7,"When someone snaps at me, I spend the rest of the day thinking about it", answers));
        questions.add(new Question(8,"No matter what I do, I can't get my mind off my problems", answers));

        tests.add(new Test(1,"Evgeny","Psychology","Test 1","Психологический тест",new Date(),questions));

        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();

        answers.add(new Answer(1,"yes",1));
        answers.add(new Answer(2,"i don't know",1));
        answers.add(new Answer(3,"no",1));

        questions.add(new Question(1," Если я дома не один, никогда не снимаю телефонную трубку, надеясь, что это сделает кто-то другой. Если я один, то выжидаю 3-4 звонка, прежде чем ответить. ", answers));
        questions.add(new Question(2,"Часто опаздываю на работу или на встречу, поскольку встаю с постели в последний момент. ", answers));
        questions.add(new Question(3,"Объезжаю места парковки по несколько раз в поисках самого удобного, чтобы не идти пешком.", answers));
        questions.add(new Question(4,"I am as happy as the people around me", answers));
        questions.add(new Question(5,"I have a dry mouth", answers));
        questions.add(new Question(6,"When someone snaps at me, I spend the rest of the day thinking about it", answers));
        questions.add(new Question(7,"No matter what I do, I can't get my mind off my problems", answers));

        tests.add(new Test(2,"Igor","Funny","Test 2","Funny fucking crap",new Date(),questions));


        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();

        answers.add(new Answer(1,"yes",1));
        answers.add(new Answer(2,"rather yes",1));
        answers.add(new Answer(3,"i don't know",2));
        answers.add(new Answer(4,"rather no",1));
        answers.add(new Answer(5,"no",5));

        questions.add(new Question(1,"Объезжаю места парковки по несколько раз в поисках самого удобного, чтобы не идти пешком.", answers));
        questions.add(new Question(2,"Смотрю телевизор или читаю почти всегда лёжа на диване. ", answers));
        questions.add(new Question(3,"2+5?", answers));
        questions.add(new Question(4,"I am as happy as the people around me", answers));
        questions.add(new Question(5,"I have diarrhea, constipation, or other digestive problems", answers));

        tests.add(new Test(3,"Igor2","Math","Test 3","blablablablabla",new Date(),questions));

        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();

        answers.add(new Answer(1,"Yes",1));
        answers.add(new Answer(2,"No",1));

        questions.add(new Question(1," Does it work?", answers));
        questions.add(new Question(2,"Does it really work?", answers));

        tests.add(new Test(4,"Lightless","TEST","Test for testing","Random questions to waste your time",new Date(),questions));
    }
}
