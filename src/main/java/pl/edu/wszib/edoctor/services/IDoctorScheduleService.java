package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleService {
    List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId);
    DoctorSchedule getDoctorScheduleById(int doctorScheduleId);
    void updateDoctorSchedule(DoctorSchedule doctorSchedule);
}
