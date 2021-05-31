package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IDoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorByDoctorId(int doctorId);
    Doctor getDoctorByUserId(int userId);
    boolean addDoctor(Doctor doctor, User user);
    void deleteDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);

}
