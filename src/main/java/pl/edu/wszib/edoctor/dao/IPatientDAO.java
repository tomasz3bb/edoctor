package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IPatientDAO {
    Patient getPatientByPatientId(int patientId);
    Patient getPatientByUserId(int userId);
    List<Patient> getAllPatients();
    void deletePatient(Patient patient);
    void updatePatient(Patient patient);
    boolean addPatient(Patient patient);
}
