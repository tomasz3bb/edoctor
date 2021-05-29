package pl.edu.wszib.edoctor.model;

import javax.persistence.*;

@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        ADMIN,
        Pacjent,
        Lekarz
    }

    public User() {
    }

    public User(int userId, String login, String password, Role role) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return userId + " " + login + " " + role;
    }
}
