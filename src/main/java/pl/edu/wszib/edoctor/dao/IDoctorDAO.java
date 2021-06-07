package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IDoctorDAO{
    Doctor getDoctorByDoctorId(int doctorId);
    Doctor getDoctorByUserId(int userId);
    List<Doctor> getAll();
    List<Doctor> getAllBySpeciality(Speciality speciality);
    void delete(Doctor doctor);
    void update(Doctor doctor);
    boolean save(Doctor doctor);
}
