package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IAppointmentDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.model.Speciality;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;
@Repository
public class AppointmentDAOImpl implements IAppointmentDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Appointment> getAppointmentByPatient(Patient patient) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Appointment where patient = :patient");
        appointmentQuery.setParameter("patient", patient);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppointmentByDoctor(Doctor doctor) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Appointment where doctor = :doctor");
        appointmentQuery.setParameter("doctor", doctor);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getPatientAppointmentByDoctor(Doctor doctor, Patient patient) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Appointment where doctor = :doctor and patient =:patient");
        appointmentQuery.setParameter("doctor", doctor);
        appointmentQuery.setParameter("patient", patient);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppByStatus(Patient patient, Appointment.Status status) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Appointment where patient = :patient and status = :status order by appointmentDate");
        appointmentQuery.setParameter("patient", patient);
        appointmentQuery.setParameter("status", status);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppByStatusAndDate(Patient patient, Appointment.Status status, Date keyword) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("FROM pl.edu.wszib.edoctor.model.Appointment where patient = :patient " +
                "and status = :status and appointmentDate =: keyword");
        appointmentQuery.setParameter("patient", patient);
        appointmentQuery.setParameter("status", status);
        appointmentQuery.setParameter("keyword", keyword);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppByDoctorAndDate(Doctor doctor, Date date) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointment = session.createQuery("from pl.edu.wszib.edoctor.model.Appointment where doctor = : doctor and appointmentDate =: date");
        appointment.setParameter("doctor", doctor);
        appointment.setParameter("date", date);
        List<Appointment> appointmentList = appointment.getResultList();
        session.close();
        return appointmentList;
    }

    @Override
    public Appointment getById(int appointmentId) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Appointment where appointmentId =: appointmentId");
        appointmentQuery.setParameter("appointmentId", appointmentId);
        Appointment appointment = null;
        try {
            appointment = appointmentQuery.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        session.close();
        return appointment;
    }

    @Override
    public boolean save(Appointment appointment) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(appointment);
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
    public boolean update(Appointment appointment) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(appointment);
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
    public boolean delete(Appointment appointment) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(appointment);
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
