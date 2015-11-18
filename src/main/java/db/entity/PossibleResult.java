package db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "possible_results")
public class PossibleResult {
    private int id;
    private String text;
    private int minLimit;
    private int maxLimit;
    private Test test;
    Set<UserPass> userPassSet = new HashSet<UserPass>();

    public PossibleResult(){}

    public PossibleResult(String text, int minLimit, int maxLimit, Test test){
        this.text = text;
        this.minLimit=minLimit;
        this.maxLimit=maxLimit;
        this.test=test;
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

    @Column(name = "min_limit")
    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    @Column(name = "max_limit")
    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test", nullable = false)
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "result")
    public Set<UserPass> getUserPassSet() {
        return userPassSet;
    }

    public void setUserPassSet(Set<UserPass> userPassSet) {
        this.userPassSet = userPassSet;
    }


}
