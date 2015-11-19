package db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answers")
public class Answer {

    private int idAnswer;
    private String title;
    private int number;
    private Question question;
    private int weight;
    private int isChoosed;
    private Set<UserAnswer> userAnswers = new HashSet<UserAnswer>();

    public Answer(){}

    public Answer(int number, String title) {
        this.title = title;
        this.number = number;
    }

    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    @Column(name="number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_question", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "answer")
    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Column(name="weight")
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Column(name="is_choosed")
    public int getIsChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(int isChoosed) {
        this.isChoosed = isChoosed;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "idAnswer=" + idAnswer +
                ", title='" + title + '\'' +
                ", number=" + number +
                ", question=" + question +
                ", weight=" + weight +
                ", isChoosed=" + isChoosed +
                ", userAnswers=" + userAnswers +
                '}';
    }
}
