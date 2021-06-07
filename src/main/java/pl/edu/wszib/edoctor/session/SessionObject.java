package pl.edu.wszib.edoctor.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;

@Component
@SessionScope
public class SessionObject {
    private User loggedUser = null;
    private String info = null;

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        String temp = this.info;
        this.info = null;
        return temp;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLogged() {
        return this.loggedUser != null;
    }


}
