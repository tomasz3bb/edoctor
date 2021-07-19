package pl.edu.wszib.edoctor.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.dao.IUserDAO;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.ChangePasswordModel;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;
import pl.edu.wszib.edoctor.services.IUserService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.io.IOException;
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
        User newUser = new User(0, user.getLogin(), user.getPassword(), user.getRole(), user.getPhoto());
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
        user.setPhoto(user.getPhoto());
        this.userDAO.update(userFromDB);
    }

    @Override
    public boolean authenticate(User user) {
        User userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if (userFromDatabase == null){
            return false;
        }
        if (userFromDatabase.getPassword().equals(user.getPassword())) {
            this.sessionObject.setLoggedUser(userFromDatabase);
        }
        return true;
    }

    @Override
    public boolean register(RegistrationModel registrationModel, Patient patient) {
        if (this.userDAO.getUserByLogin(registrationModel.getLogin()) != null) {
            return false;
        }
        User newUser = new User(0, registrationModel.getLogin(), registrationModel.getPass(), User.Role.Pacjent, registrationModel.getPhoto());
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

    @Override
    public boolean updatePassword(User user, ChangePasswordModel changePasswordModel) {
        User userFromDB = this.userDAO.getUserById(user.getUserId());
        if (!userFromDB.getPassword().equals(changePasswordModel.getOldPass())
                || ! changePasswordModel.getNewPass().equals(changePasswordModel.getConfirmPass())){
            return false;
        }else {
            userFromDB.setPassword(changePasswordModel.getNewPass());
            return this.userDAO.update(userFromDB);
        }
    }

    @Override
    public boolean uploadPhoto(User user, MultipartFile image) {
        User userFromDB = this.sessionObject.getLoggedUser();
        try {
            Byte[] byteObjects = new Byte[image.getBytes().length];
            int i = 0;
            for (byte b : image.getBytes()){
                byteObjects[i++] = b;
            }
            userFromDB.setPhoto(byteObjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.userDAO.update(userFromDB);
    }
}
