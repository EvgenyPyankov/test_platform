package bd;

import java.util.ArrayList;
import java.util.Date;

public class Test {
    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTestCategory() {
        return testCategory;
    }

    public void setTestCategory(String testCategory) {
        this.testCategory = testCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    protected int idTest;
    private String author;
    private String testCategory;
    private String title;
    private String description;
    private Date date;
    private ArrayList<Question> questions;


    public Test(int idTest,String author,String testCategory,String title,String description,Date date,ArrayList<Question>questions){
        this.idTest=idTest;
        this.author=author;
        this.testCategory=testCategory;
        this.title=title;
        this.description=description;
        this.date=date;
        this.questions=questions;
    }


}
