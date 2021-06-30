package pl.edu.wszib.edoctor.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordModel {
    private String oldPass;
    private String newPass;
    private String confirmPass;
}
