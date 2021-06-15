package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IDoctorService {
    List<Doctor> getAll();
    Doctor getDoctorByDoctorId(int doctorId);
    Doctor getDoctorByUserId(int userId);
    boolean save(Doctor doctor, User user);
    boolean delete(Doctor doctor);
    boolean update(Doctor doctor);

}
