package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.dao.IUserDAO;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    IDoctorDAO doctorDAO;

    @Autowired
    IPatientDAO patientDAO;

    @Override
    public boolean save(User user) {
        User newUser = new User(0, user.getLogin(), user.getPassword(), user.getRole());
        return this.userDAO.save(newUser);
    }

    @Override
    public void delete(User user) {
        this.userDAO.delete(user);
    }

    @Override
    public void update(User user) {
        User userFromDB = this.userDAO.getUserById(user.getUserId());
        userFromDB.setLogin(user.getLogin());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setRole(user.getRole());
        this.userDAO.update(userFromDB);
    }

    @Override
    public boolean authenticate(User user) {
        User userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if (userFromDatabase == null){
            return false;
        }
        if (user.getPassword().equals(userFromDatabase.getPassword())){
            this.sessionObject.setLoggedUser(userFromDatabase);
        }
        return true;
    }

    @Override
    public boolean register(RegistrationModel registrationModel, Patient patient) {
        if (this.userDAO.getUserByLogin(registrationModel.getLogin()) != null) {
            return false;
        }
        User newUser = new User(0, registrationModel.getLogin(), registrationModel.getPass(), User.Role.Pacjent);
        Patient newPatient = new Patient(0, newUser, patient.getName(), patient.getSurname(),
                patient.getPhone(), patient.getDateOfBirth(), patient.getPESEL());
        this.userDAO.save(newUser);
        return this.patientDAO.save(newPatient);
    }

    @Override
    public void logout() {
        this.sessionObject.setLoggedUser(null);
    }

    @Override
    public User getUserById(int id) {
        return this.userDAO.getUserById(id);
    }

    @Override
    public List<User> getAll() {
        return this.userDAO.getAll();
    }



}
