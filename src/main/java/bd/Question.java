package bd;

import java.util.ArrayList;

public class Question {
    private int number;
    private String title;
    private ArrayList<Answer> answers;

    public Question(int number, String title,ArrayList<Answer> answers){
        this.number=number;
        this.title=title;
        this.answers = answers;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
