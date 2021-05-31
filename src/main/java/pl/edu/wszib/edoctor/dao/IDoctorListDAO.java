package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IDoctorListDAO {
    List<DoctorList> getPatientsByDoctor(Doctor doctor);
    List<DoctorList> getDoctorsByPatient(Patient patient);
}
