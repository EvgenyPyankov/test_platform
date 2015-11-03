package bd;

import java.util.ArrayList;

public class Question {
    protected int number;
    protected String title;
    protected ArrayList<Answer> answers;

    public Question(int number, String title,ArrayList<Answer> answers){
        this.number=number;
        this.title=title;
        this.answers = answers;
    }
}
