package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;

import java.util.List;

public interface IDoctorListService {

    List<DoctorList> getDoctorsByPatient(Patient patient);
    List<DoctorList> getPatientsByDoctor(Doctor doctor);

    boolean savePatientToDoctor(DoctorList doctorList, int doctorId);
}
