package db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {
    private int idQuestion;
    private String title;
    private int number;
    private Test test;
    private Set<Answer> answers = new HashSet<Answer>();
    private int questionType; // 1 - radio, 2 - checkbox, 3 - text
    private Set<UserAnswer> userAnswers = new HashSet<UserAnswer>();
    private String answerText;

    public Question(){}



    public Question(int number, String title, Set<Answer>answers, int questionType){
        this.title = title;
        this.number = number;
        this.answers = answers;
        this.questionType = questionType;
    }

    public Question(int number,String title, int questionType){
        this.title = title;
        this.number = number;
        this.questionType = questionType;
    }

    @Id
    @Column(name = "id_question")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_test", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question")
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Column(name="question_type")
    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question")
    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Column(name="answer_text")
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

//    @Override
//    public String toString() {
//        return "Question{" +
//                "idQuestion=" + idQuestion +
//                ", title='" + title + '\'' +
//                ", number=" + number +
//                ", test=" + test +
//                ", answers=" + answers +
//                ", questionType=" + questionType +
//                ", userAnswers=" + userAnswers +
//                ", answerText='" + answerText + '\'' +
//                '}';
//    }
}
