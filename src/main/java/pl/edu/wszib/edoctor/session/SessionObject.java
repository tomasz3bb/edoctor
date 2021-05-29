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
    private Doctor loggedDoctor = null;
    private Patient loggedPatient = null;

    public Doctor getLoggedDoctor() {
        return loggedDoctor;
    }

    public void setLoggedDoctor(Doctor loggedDoctor) {
        this.loggedDoctor = loggedDoctor;
    }

    public Patient getLoggedPatient() {
        return loggedPatient;
    }

    public void setLoggedPatient(Patient loggedPatient) {
        this.loggedPatient = loggedPatient;
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

    public boolean isDoctorLogged(){
        return this.loggedDoctor != null;
    }

    public boolean isPatientLogged(){
        return this.loggedPatient != null;
    }

}
