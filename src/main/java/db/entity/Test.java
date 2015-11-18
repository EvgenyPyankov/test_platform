package db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests")
public class Test {
    private int idTest;
    private String title;
    private TestCategory testCategory;
    private Set<Question>questions = new HashSet<Question>();
    private String description;
    private Date date;
    private User author;
    private Set<PossibleResult> possibleResults = new HashSet<PossibleResult>();
    private Set<UserPass> userPassSet = new HashSet<UserPass>();
    private String textResult;


    public Test(){
        date = new Date();
    }

    public Test(String title, TestCategory testCategory,Set<Question> questions, User author){
        this.title = title;
        this.testCategory=testCategory;
        this.questions=questions;
        date = new Date();
        this.author=author;
    }



    @Id
    @Column(name = "id_test")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "test_category")
    @Enumerated(EnumType.STRING)
    public TestCategory getTestCategory() {
        return testCategory;
    }

    public void setTestCategory(TestCategory testCategory) {
        this.testCategory = testCategory;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "test")
    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_author", nullable = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "test")
    public Set<PossibleResult> getPossibleResults() {
        return possibleResults;
    }

    public void setPossibleResults(Set<PossibleResult> possibleResults) {
        this.possibleResults = possibleResults;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "test")
    public Set<UserPass> getUserPassSet() {
        return userPassSet;
    }

    public void setUserPassSet(Set<UserPass> userPassSet) {
        this.userPassSet = userPassSet;
    }

    @Column(name="text_result")
    public String getTextResult() {
        return textResult;
    }

    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }


    @Override
    public String toString() {
        return "Test{" +
                "idTest=" + idTest +
                ", title='" + title + '\'' +
                ", testCategory=" + testCategory +
                ", questions=" + questions +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", author=" + author +
                ", possibleResults=" + possibleResults +
                ", userPassSet=" + userPassSet +
                ", textResult='" + textResult + '\'' +
                '}';
    }
}
