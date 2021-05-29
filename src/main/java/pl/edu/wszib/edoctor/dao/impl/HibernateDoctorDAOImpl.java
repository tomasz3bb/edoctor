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
public class HibernateDoctorDAOImpl implements IDoctorDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<DoctorSchedule> getCurrentDoctorSchedule(int doctorId) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorSchedule> query = session.createQuery("FROM pl.edu.wszib.edoctor.model.DoctorSchedule where doctor.doctorId  = : doctorId");
        query.setParameter("doctorId", doctorId);
        List<DoctorSchedule> doctorScheduleList = query.getResultList();
        session.close();
        return doctorScheduleList;
    }

    @Override
    public List<DoctorList> getPatientsByDoctor(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorList> patientQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.DoctorList where doctor = : doctor");
        patientQuery.setParameter("doctor", doctor);
        List<DoctorList> patientList = patientQuery.getResultList();
        session.close();
        return patientList;
    }

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
    public List<Doctor> getAllDoctors() {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Doctor");
        List<Doctor> doctors = doctorQuery.getResultList();
        session.close();
        return doctors;
    }

    @Override
    public List<Doctor> getAllDoctorsBySpeciality(Speciality speciality) {
        Session session = this.sessionFactory.openSession();
        Query<Doctor> doctorQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Doctor where speciality = : speciality");
        doctorQuery.setParameter("speciality", speciality);
        List<Doctor> doctorList = doctorQuery.getResultList();
        session.close();
        return doctorList;
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
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
    }

    @Override
    public void updateDoctor(Doctor doctor) {
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
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
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
