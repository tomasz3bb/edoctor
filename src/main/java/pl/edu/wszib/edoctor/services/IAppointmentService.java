package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> getAppointmentByPatient(int userId);
}
