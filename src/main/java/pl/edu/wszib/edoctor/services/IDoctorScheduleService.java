package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleService {
    List<DoctorSchedule> getAllByDoctorId(int doctorId);
    List<DoctorSchedule> getAllByDoctor(Doctor doctor);
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    void delete(DoctorSchedule doctorSchedule);
    void save(DoctorSchedule doctorSchedule);
    void update(DoctorSchedule doctorSchedule);
}
