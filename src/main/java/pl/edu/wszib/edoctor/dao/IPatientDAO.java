package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IPatientDAO {
    Patient getPatientByPatientId(int patientId);
    Patient getPatientByUserId(int userId);
    List<Patient> getAll();
    boolean delete(Patient patient);
    boolean update(Patient patient);
    boolean save(Patient patient);
}
