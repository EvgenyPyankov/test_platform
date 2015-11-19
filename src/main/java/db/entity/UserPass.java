package db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_pass")
public class UserPass {
    private int id;
    private Date date;
    private PossibleResult result;
    private Test test;
    private User user;
    private Set<UserAnswer> userAnswers = new HashSet<UserAnswer>();

    public UserPass() {}

    public UserPass(PossibleResult result, Test test, User user) {
        this.result = result;
        this.test = test;
        this.user = user;
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

    @Column(name="date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_result")
    public PossibleResult getResult() {
        return result;
    }

    public void setResult(PossibleResult result) {
        this.result = result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userPass")
    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Override
    public String toString() {
        return "UserPass{" +
                "id=" + id +
                ", date=" + date +
                ", userAnswers=" + userAnswers +
                ", test=" + test.getTitle() +
                ", user=" + user.getLogin() +
                '}';
    }
}
