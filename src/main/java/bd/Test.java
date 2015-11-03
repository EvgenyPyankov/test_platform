package bd;

import java.util.ArrayList;
import java.util.Date;

public class Test {
    protected int idTest;
    protected String author;
    protected String testCategory;
    protected String title;
    protected String description;
    protected Date date;
    protected ArrayList<Question> questions;


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
