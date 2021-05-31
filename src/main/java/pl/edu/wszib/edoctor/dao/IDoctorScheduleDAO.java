package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleDAO {
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId);
    List<DoctorSchedule> getDoctorScheduleByDoctor(Doctor doctor);
    void addDoctorSchedule(DoctorSchedule doctorSchedule);
    void deleteDoctorSchedule(DoctorSchedule doctorSchedule);
    void updateDoctorSchedule(DoctorSchedule doctorSchedule);
}
