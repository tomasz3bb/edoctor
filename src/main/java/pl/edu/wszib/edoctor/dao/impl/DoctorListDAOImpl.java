package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IDoctorListDAO;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

@Repository
public class DoctorListDAOImpl implements IDoctorListDAO {

    @Autowired
    SessionFactory sessionFactory;

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
    public List<DoctorList> getDoctorsByPatient(Patient patient) {
        Session session = this.sessionFactory.openSession();
        Query<DoctorList> doctorQuery= session.createQuery("FROM pl.edu.wszib.edoctor.model.DoctorList where patient = : patient");
        doctorQuery.setParameter("patient", patient);
        List<DoctorList> doctors = doctorQuery.getResultList();
        session.close();
        return doctors;
    }

    @Override
    public boolean savePatientToDoctor(DoctorList doctorList) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(doctorList);
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
