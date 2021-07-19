package pl.edu.wszib.edoctor.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationModel {
    private String login;
    private String pass;
    private String pass2;
    private Byte[] photo;
}
