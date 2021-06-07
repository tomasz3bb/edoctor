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
import java.util.ArrayList;
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
    public List<DoctorSchedule> getAllByDoctorId(int doctorId) {
        return this.doctorScheduleDAO.getAllByDoctorId(doctorId);
    }

    @Override
    public List<DoctorSchedule> getAllByDoctor(Doctor doctor) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        return this.doctorScheduleDAO.getAllByDoctor(loggedDoctor);
    }

    @Override
    public void delete(DoctorSchedule doctorSchedule) {
        DoctorSchedule doctorScheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        this.doctorScheduleDAO.delete(doctorScheduleFromDB);
    }

    @Override
    public void update(DoctorSchedule doctorSchedule) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        DoctorSchedule doctorScheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        doctorScheduleFromDB.setDoctor(loggedDoctor);
        doctorScheduleFromDB.setDayOfWeek(doctorSchedule.getDayOfWeek());
        doctorScheduleFromDB.setStartOfWork(doctorSchedule.getStartOfWork());
        doctorScheduleFromDB.setEndOfWork(doctorSchedule.getEndOfWork());

        this.doctorScheduleDAO.update(doctorScheduleFromDB);
    }

    @Override
    public void save(DoctorSchedule doctorSchedule) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        DoctorSchedule newDoctorSchedule = new DoctorSchedule(0, loggedDoctor, doctorSchedule.getDayOfWeek(),
                doctorSchedule.getStartOfWork(), doctorSchedule.getEndOfWork());
            this.doctorScheduleDAO.save(newDoctorSchedule);
    }
}
