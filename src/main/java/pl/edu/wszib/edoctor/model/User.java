package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
