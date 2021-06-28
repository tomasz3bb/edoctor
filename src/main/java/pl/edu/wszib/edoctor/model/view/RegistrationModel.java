package pl.edu.wszib.edoctor.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationModel {
    private String login;
    private String pass;
    private String pass2;
}
