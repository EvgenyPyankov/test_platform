package bd;

import java.util.ArrayList;
import java.util.Date;

public class DBContorller implements DBControllerMethods{
    ArrayList<Test> tests = new ArrayList<>();
    public DBContorller(){
        createTests();
    }

    public Test getTestById(int id){
       for (Test item:tests)
           if (item.idTest==id)
               return item;
        System.err.println("Not found!");
        return null;
    }

    public ArrayList<Test> getTests(){
        return tests;
    }

    private void createTests(){
        ArrayList<Question> questions;
        ArrayList<Answer> answers;

        questions = new ArrayList<>();
        answers = new ArrayList<>();

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

        questions = new ArrayList<>();
        answers = new ArrayList<>();

        answers.add(new Answer(1,"yes",1));
        answers.add(new Answer(2,"i don't know",1));
        answers.add(new Answer(3,"no",1));

        questions.add(new Question(1,"", answers));
        questions.add(new Question(2,"В магазине вы увидели потрясающие кроссовки. Вы", answers));
        questions.add(new Question(3,"Что вы думаете о следующем высказывании: \"В гардеробе действительно элегантного мужчины должно быть не менее 10 галстуков\"?", answers));
        questions.add(new Question(4,"I am as happy as the people around me", answers));
        questions.add(new Question(5,"I have a dry mouth", answers));
        questions.add(new Question(6,"When someone snaps at me, I spend the rest of the day thinking about it", answers));
        questions.add(new Question(7,"No matter what I do, I can't get my mind off my problems", answers));

        tests.add(new Test(2,"Igor","Funny","Test 2","Funny fucking crap",new Date(),questions));


        questions = new ArrayList<>();
        answers = new ArrayList<>();

        answers.add(new Answer(1,"yes",1));
        answers.add(new Answer(2,"rather yes",1));
        answers.add(new Answer(3,"i don't know",2));
        answers.add(new Answer(4,"rather no",1));
        answers.add(new Answer(5,"no",5));

        questions.add(new Question(1,"Где вы предпочитаете носить носовой платок?\n", answers));
        questions.add(new Question(2,"Мужчины, одевающиеся в лучших традициях авангарда, на ваш взгляд, обладают следующими качествами", answers));
        questions.add(new Question(3,"2+5?", answers));
        questions.add(new Question(4,"I am as happy as the people around me", answers));
        questions.add(new Question(5,"I have diarrhea, constipation, or other digestive problems", answers));

        tests.add(new Test(3,"Igor2","Math","Test 3","blablablablabla",new Date(),questions));
    }
}
