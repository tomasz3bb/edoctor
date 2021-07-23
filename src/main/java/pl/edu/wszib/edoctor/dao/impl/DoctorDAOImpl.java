package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.model.*;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class DoctorDAOImpl implements IDoctorDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Doctor getDoctorByUserId(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Doctor where user.userId = :userId");
        doctorQuery.setParameter("userId", userId);
        Doctor doctor = null;
        try {
            doctor = doctorQuery.getSingleResult();
        }catch (NoResultException e){
            System.out.println("Nie znalezniono doktora");
        }
        session.close();
        return doctor;
    }

    @Override
    public Doctor getDoctorByDoctorId(int doctorId) {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Doctor where doctorId = :doctorId");
        doctorQuery.setParameter("doctorId", doctorId);
        Doctor doctor = null;
        try {
            doctor = doctorQuery.getSingleResult();
        }catch (NoResultException e){
            System.out.println("Nie znalezniono doktora");
        }
        session.close();
        return doctor;
    }

    @Override
    public List<Doctor> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Doctor order by speciality.specialityName");
        List<Doctor> doctors = doctorQuery.getResultList();
        session.close();
        return doctors;
    }

    @Override
    public List<Doctor> findByKeyword(String keyword) {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Doctor where name =: keyword or surname =: keyword or speciality.specialityName =:keyword");
        doctorQuery.setParameter("keyword", keyword);
        List<Doctor> doctorList = doctorQuery.getResultList();
        session.close();
        return doctorList;
    }

    @Override
    public List<Doctor> getAllBySpeciality(Speciality speciality) {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Doctor where speciality = : speciality");
        doctorQuery.setParameter("speciality", speciality);
        List<Doctor> doctorList = doctorQuery.getResultList();
        session.close();
        return doctorList;
    }

    @Override
    public boolean delete(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(doctor);
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
    public boolean update(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(doctor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean save(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(doctor);
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
