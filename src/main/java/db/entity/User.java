package db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private int idUser;
    private String login;
    private int password;
    private String email;
    private Set<Test> tests = new HashSet<Test>();
    private Set<UserPass> userPassSet = new HashSet<UserPass>();

    public User(){}

    public User(String login, String email, int password){
        this.login = login;
        this.email=email;
        this.password=password;
    }

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name="password")
    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public Set<UserPass> getUserPassSet() {
        return userPassSet;
    }

    public void setUserPassSet(Set<UserPass> userPassSet) {
        this.userPassSet = userPassSet;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "idUser=" + idUser +
//                ", login='" + login + '\'' +
//                ", password=" + password +
//                ", email='" + email + '\'' +
//                ", tests=" + tests +
//                ", userPassSet=" + userPassSet +
//                '}';
//    }
}
