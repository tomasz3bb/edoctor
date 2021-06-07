package pl.edu.wszib.edoctor.model.view;

public class RegistrationModel {
    private String login;
    private String pass;
    private String pass2;

    public RegistrationModel() {
    }

    public RegistrationModel(String login, String pass, String pass2) {
        this.login = login;
        this.pass = pass;
        this.pass2 = pass2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }
}
