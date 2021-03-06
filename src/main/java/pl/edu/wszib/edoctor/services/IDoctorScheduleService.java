package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleService {
    List<DoctorSchedule> getAllByDoctorId(int doctorId);
    List<DoctorSchedule> getAllByDoctor(Doctor doctor);
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    boolean delete(DoctorSchedule doctorSchedule);
    boolean save(DoctorSchedule doctorSchedule);
    boolean update(DoctorSchedule doctorSchedule);
}
