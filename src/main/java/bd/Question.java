package bd;

import java.util.ArrayList;

public class Question {
    private int number;
    private String title;
    private ArrayList<Answer> answers;
    private int questionType;

    public Question(int number, String title, ArrayList<Answer> answers,int questionType){
        this.number=number;
        this.title=title;
        this.questionType=questionType;
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

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", answers=" + answers +
                ", questionType=" + questionType +
                '}';
    }
}
