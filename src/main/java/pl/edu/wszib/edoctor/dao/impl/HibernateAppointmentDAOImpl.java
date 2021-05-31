package pl.edu.wszib.edoctor.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.edoctor.dao.IAppointmentDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;
@Repository
public class HibernateAppointmentDAOImpl implements IAppointmentDAO {

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
    public List<Appointment> getAppointmentByDoctor(Doctor doctor, Patient patient) {
        Session session = this.sessionFactory.openSession();
        Query<Appointment> appointmentQuery = session.createQuery("from pl.edu.wszib.edoctor.model.Appointment where doctor = :doctor and patient =: patient");
        appointmentQuery.setParameter("doctor", doctor);
        appointmentQuery.setParameter("patient", patient);
        List<Appointment> appointmentList = appointmentQuery.getResultList();
        session.close();
        return appointmentList;
    }
}
