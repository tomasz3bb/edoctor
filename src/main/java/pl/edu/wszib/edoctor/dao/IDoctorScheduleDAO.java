package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleDAO {
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId);
    void updateDoctorSchedule(DoctorSchedule doctorSchedule);
    void addDoctorSchedule(DoctorSchedule doctorSchedule);
    void deleteDoctorSchedule(DoctorSchedule doctorSchedule);
}
