package pl.edu.wszib.edoctor.dao;

import org.springframework.data.repository.query.Param;
import pl.edu.wszib.edoctor.model.*;

import java.util.List;

public interface IDoctorDAO{
    Doctor getDoctorByDoctorId(int doctorId);
    Doctor getDoctorByUserId(int userId);
    List<Doctor> getAll();
    List<Doctor> getAllBySpeciality(Speciality speciality);
    boolean delete(Doctor doctor);
    boolean update(Doctor doctor);
    boolean save(Doctor doctor);
    List<Doctor> findByKeyword(@Param("keyword") String keyword);
}
