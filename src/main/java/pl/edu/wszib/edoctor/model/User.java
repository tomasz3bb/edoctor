package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

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
    @Lob
    private Byte[] photo;

    public enum Role{
        ADMIN,
        Pacjent,
        Lekarz
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
