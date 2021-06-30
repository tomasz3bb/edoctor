package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.model.view.ChangePasswordModel;
import pl.edu.wszib.edoctor.model.view.ChangePhotoModel;
import pl.edu.wszib.edoctor.model.view.RegistrationModel;

import java.util.List;

public interface IUserService {
    boolean authenticate(User user);
    void logout();
    User getUserById(int id);
    boolean save(User user);
    void delete(User user);
    void update(User user);
    List<User> getAll();
    boolean register(RegistrationModel registrationModel, Patient patient);
    boolean updatePassword(User user, ChangePasswordModel changePasswordModel);
    boolean updatePhoto(User user, ChangePhotoModel changePhotoModel);
}
