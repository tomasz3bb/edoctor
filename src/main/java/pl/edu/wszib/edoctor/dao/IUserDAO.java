package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.User;

import java.util.List;

public interface IUserDAO {
    User getUserByLogin(String login);
    User getUserById(int userId);
    boolean addUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    List<User> getAllUsers();
}
