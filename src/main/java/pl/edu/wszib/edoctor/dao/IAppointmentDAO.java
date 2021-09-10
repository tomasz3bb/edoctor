package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;

import java.sql.Date;
import java.util.List;

public interface IAppointmentDAO{
    List<Appointment> getAppointmentByPatient(Patient patient);
    List<Appointment> getAppointmentByDoctor(Doctor doctor);
    List<Appointment> getPatientAppointmentByDoctor(Doctor doctor, Patient patient);
    List<Appointment> getAppByStatus(Patient patient, Appointment.Status status);
    boolean save(Appointment appointment);
    boolean update(Appointment appointment);
    boolean delete(Appointment appointment);
    Appointment getById(int appointmentId);
    List<Appointment> getAppByStatusAndDate(Patient patient, Appointment.Status status, Date keyword);
}
