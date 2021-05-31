package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IAppointmentDAO {
    List<Appointment> getAppointmentByPatient(Patient patient);
    List<Appointment> getAppointmentByDoctor(Doctor doctor, Patient patient);
}
