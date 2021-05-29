package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.User;

import java.util.List;

public interface IUserService {
    void authenticate(User user);
    void logout();
    User getUserById(int id);
    boolean addUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    List<User> getAllUsers();
}
