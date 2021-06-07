package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IPatientDAO {
    Patient getPatientByPatientId(int patientId);
    Patient getPatientByUserId(int userId);
    List<Patient> getAll();
    void delete(Patient patient);
    void update(Patient patient);
    boolean save(Patient patient);
}
