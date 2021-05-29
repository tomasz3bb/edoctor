package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IDoctorDAO {
    Doctor getDoctorByDoctorId(int doctorId);
    Doctor getDoctorByUserId(int userId);
    List<Doctor> getAllDoctors();
    List<Doctor> getAllDoctorsBySpeciality(Speciality speciality);
    void deleteDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    boolean addDoctor(Doctor doctor);
    List <DoctorSchedule> getCurrentDoctorSchedule(int doctorId);
    List<DoctorList> getPatientsByDoctor(Doctor doctor);
}
