package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.*;
import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.services.IAppSlotService;
import pl.edu.wszib.edoctor.services.IAppointmentService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class AppSlotServiceImpl implements IAppSlotService {
    @Autowired
    IAppSlotDAO appSlotDAO;
    @Autowired
    IPatientDAO patientDAO;
    @Autowired
    IDoctorDAO doctorDAO;
    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;
    @Resource
    SessionObject sessionObject;

    @Override
    public boolean addAppSlot(AppSlot appSlot, int doctorId) {
        Doctor doctor = this.doctorDAO.getDoctorByDoctorId(doctorId);
        AppSlot newAppSlot = new AppSlot(0, doctor, appSlot.getAppointmentDate(),
                appSlot.getAppointmentDate().toLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")),
                appSlot.getAppointmentTimeStart(), appSlot.getAppointmentTimeStart().plusMinutes(30), true);
        return this.appSlotDAO.save(newAppSlot);
    }

    @Override
    public boolean update(AppSlot appSlot) {
        AppSlot appSlotFromDB = this.appSlotDAO.getById(appSlot.getAppSlotId());
        appSlotFromDB.setDoctor(appSlotFromDB.getDoctor());
        appSlotFromDB.setAppointmentDate(appSlotFromDB.getAppointmentDate());
        appSlotFromDB.setDayOfWeek(appSlotFromDB.getDayOfWeek());
        appSlotFromDB.setAppointmentTimeStart(appSlotFromDB.getAppointmentTimeStart());
        appSlotFromDB.setAppointmentTimeEnd(appSlotFromDB.getAppointmentTimeEnd());
        appSlotFromDB.setAvailable(appSlotFromDB.isAvailable());
        return this.appSlotDAO.update(appSlotFromDB);
    }

    @Override
    public boolean delete(AppSlot appSlot) {
        AppSlot appSlotFromDB = this.appSlotDAO.getById(appSlot.getAppSlotId());
        return this.appSlotDAO.delete(appSlotFromDB);
    }

    @Override
    public AppSlot getById(int appointmentId) {
        return null;
    }

    @Override
    public List<AppSlot> getAllByDoctorAndDate(Doctor doctor, Date keyword) {
        Doctor doctorFromDB = this.doctorDAO.getDoctorByDoctorId(doctor.getDoctorId());
        return this.appSlotDAO.getAllByDoctorAndDate(doctorFromDB, keyword);
    }
}
