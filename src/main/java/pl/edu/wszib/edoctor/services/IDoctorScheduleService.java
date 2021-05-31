package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleService {
    List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId);
    List<DoctorSchedule> getDoctorScheduleByDoctor(Doctor doctor);
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    void deleteDoctorSchedule(DoctorSchedule doctorSchedule);
    void addDoctorSchedule(DoctorSchedule doctorSchedule);
    void updateDoctorSchedule(DoctorSchedule doctorSchedule);
}
