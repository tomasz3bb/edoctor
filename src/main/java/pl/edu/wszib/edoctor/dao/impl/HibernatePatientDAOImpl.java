package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernatePatientDAOImpl implements IPatientDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Patient getPatientByPatientId(int patientId) {
        Session session = this.sessionFactory.openSession();
        Query<Patient> patientQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Patient where patientId = :patientId");
        patientQuery.setParameter("patientId", patientId);
        Patient patient = null;
        try {
            patient = patientQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono pacjenta!");
        }
        session.close();
        return patient;
    }

    @Override
    public Patient getPatientByUserId(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<Patient> patientQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Patient where user.userId = :userId");
        patientQuery.setParameter("userId", userId);
        Patient patient = null;
        try {
            patient = patientQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono pacjenta!");
        }
        session.close();
        return patient;
    }

    @Override
    public List<Patient> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Patient> patientQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Patient");
        List<Patient> patients = patientQuery.getResultList();
        session.close();
        return patients;
    }

    @Override
    public void delete(Patient patient) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(patient);
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
    public void update(Patient patient) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(patient);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Patient patient) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(patient);
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
