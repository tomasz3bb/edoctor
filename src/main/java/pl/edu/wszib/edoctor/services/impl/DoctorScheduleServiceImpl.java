package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;

import java.util.List;
@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService {

    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;

    @Override
    public List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId) {
        return this.doctorScheduleDAO.getDoctorScheduleByDoctorId(doctorId);
    }

    @Override
    public void updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        DoctorSchedule doctorScheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        doctorScheduleFromDB.setStartOfWork(doctorSchedule.getStartOfWork());
        doctorScheduleFromDB.setEndOfWork(doctorSchedule.getEndOfWork());

        this.doctorScheduleDAO.updateDoctorSchedule(doctorScheduleFromDB);
    }

    @Override
    public DoctorSchedule getDoctorScheduleById(int doctorScheduleId) {
        return this.doctorScheduleDAO.getDoctorScheduleById(doctorScheduleId);
    }
}
