package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.User;

import java.util.List;

public interface IUserDAO {
    User getUserByLogin(String login);
    User getUserById(int userId);
    boolean save(User user);
    void delete(User user);
    void update(User user);
    List<User> getAll();
    boolean persist(User user);
}
