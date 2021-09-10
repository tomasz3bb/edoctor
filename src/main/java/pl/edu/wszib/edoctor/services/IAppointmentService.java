package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;

import java.sql.Date;
import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAllAppointmentByPatient(int userId);
    List<Appointment> getAllPatientAppointmentByDoctor(Doctor doctor, int patientId);
    List<Appointment> getAllAppointmentByDoctor(Doctor doctor);
    List<Appointment> getCurrentAppByPatient(int userId, Appointment.Status status);
    List<Appointment> getHistAppByPatient(int userId,  Appointment.Status status);
    boolean addAppointment(int appSlotId);
    boolean update(Appointment appointment);
    boolean delete(Appointment appointment);
    Appointment getById(int appointmentId);
    List<Appointment> getCurrentAppByPatientAndDate(int userId, Appointment.Status status, Date keyword);
}
