package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IPatientService {
    List<Patient> getAllPatients();
    Patient getPatientByPatientId(int patientId);
    Patient getPatientByUserId(int userId);
    boolean addPatient(Patient patient, User user);
    void deletePatient(Patient patient);
    void updatePatient(Patient patient);

}
