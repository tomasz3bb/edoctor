package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.ISpecialityDAO;
import pl.edu.wszib.edoctor.model.Speciality;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class SpecialityDAOImpl implements ISpecialityDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Speciality getSpecialityById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Speciality> specialityQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Speciality where id = :id");
        specialityQuery.setParameter("id", id);
        Speciality speciality = null;
        try {
            speciality = specialityQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono doktora!");
        }
        session.close();
        return speciality;
    }

    @Override
    public List<Speciality> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Speciality> specialityQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Speciality");
        List<Speciality> specialities = specialityQuery.getResultList();
        session.close();
        return specialities;
    }

    @Override
    public boolean delete(Speciality speciality) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(speciality);
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
    public boolean update(Speciality speciality) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(speciality);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean save(Speciality speciality) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(speciality);
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
}
