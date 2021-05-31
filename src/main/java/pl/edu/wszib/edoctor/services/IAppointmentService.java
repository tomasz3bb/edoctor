package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAppointmentByPatient(int userId);
    List<Appointment> getAppointmentByDoctor(Doctor doctor, Patient patient);
}
