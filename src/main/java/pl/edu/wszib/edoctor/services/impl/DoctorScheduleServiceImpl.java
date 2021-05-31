package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService {

    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;

    @Autowired
    IDoctorDAO doctorDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public DoctorSchedule getDoctorScheduleById(int doctorScheduleId) {
        return this.doctorScheduleDAO.getDoctorScheduleById(doctorScheduleId);
    }

    @Override
    public List<DoctorSchedule> getDoctorScheduleByDoctorId(int doctorId) {
        return this.doctorScheduleDAO.getDoctorScheduleByDoctorId(doctorId);
    }

    @Override
    public List<DoctorSchedule> getDoctorScheduleByDoctor(Doctor doctor) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        return this.doctorScheduleDAO.getDoctorScheduleByDoctor(loggedDoctor);
    }

    @Override
    public void deleteDoctorSchedule(DoctorSchedule doctorSchedule) {
        DoctorSchedule doctorScheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        this.doctorScheduleDAO.deleteDoctorSchedule(doctorScheduleFromDB);
    }

    @Override
    public void updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        DoctorSchedule doctorScheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        doctorScheduleFromDB.setDoctor(loggedDoctor);
        doctorScheduleFromDB.setDayOfWeek(doctorSchedule.getDayOfWeek());
        doctorScheduleFromDB.setStartOfWork(doctorSchedule.getStartOfWork());
        doctorScheduleFromDB.setEndOfWork(doctorSchedule.getEndOfWork());

        this.doctorScheduleDAO.updateDoctorSchedule(doctorScheduleFromDB);
    }

    @Override
    public void addDoctorSchedule(DoctorSchedule doctorSchedule) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        DoctorSchedule newDoctorSchedule = new DoctorSchedule(0, loggedDoctor, doctorSchedule.getDayOfWeek(),
                doctorSchedule.getStartOfWork(), doctorSchedule.getEndOfWork());
        this.doctorScheduleDAO.addDoctorSchedule(newDoctorSchedule);
    }
}
