package db.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    private int id;
    private String text;
    private UserPass userPass;
    private Question question;
    private Answer answer;

    public UserAnswer() {}

    public UserAnswer(String text, UserPass userPass, Question question, Answer answer) {
        this.text = text;
        this.userPass = userPass;
        this.question = question;
        this.answer = answer;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_pass", nullable = false)
    public UserPass getUserPass() {
        return userPass;
    }

    public void setUserPass(UserPass userPass) {
        this.userPass = userPass;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_question", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_answer")
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


//    @Override
//    public String toString() {
//        return "UserAnswer{" +
//                "id=" + id +
//                '}';
//    }
}
