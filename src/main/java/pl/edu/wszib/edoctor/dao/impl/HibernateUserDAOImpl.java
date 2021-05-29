package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IUserDAO;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.User;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateUserDAOImpl implements IUserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean addUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public User getUserByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.edu.wszib.edoctor.model.User WHERE login = :login");
        query.setParameter("login", login);
        User result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.printf("Nie znaleziono usera! ");
        }
        session.close();
        return result;
    }

    @Override
    public User getUserById(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.edu.wszib.edoctor.model.User WHERE userId = :userId");
        query.setParameter("userId", userId);
        User result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.printf("Nie znaleziono usera! ");
        }
        session.close();
        return result;
    }

    @Override
    public boolean deleteUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.sessionFactory.openSession();
        Query<User> userQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.User");
        List<User> users = userQuery.getResultList();
        session.close();
        return users;
    }
}
