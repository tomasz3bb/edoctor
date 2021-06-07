package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IPatientService {
    List<Patient> getAll();
    Patient getPatientByPatientId(int patientId);
    Patient getPatientByUserId(int userId);
    boolean save(Patient patient, User user);
    void delete(Patient patient);
    void update(Patient patient);

}
