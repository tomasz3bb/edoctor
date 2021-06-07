package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleDAO {
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    List<DoctorSchedule> getAllByDoctorId(int doctorId);
    List<DoctorSchedule> getAllByDoctor(Doctor doctor);
    void save(DoctorSchedule doctorSchedule);
    void delete(DoctorSchedule doctorSchedule);
    void update(DoctorSchedule doctorSchedule);
}
