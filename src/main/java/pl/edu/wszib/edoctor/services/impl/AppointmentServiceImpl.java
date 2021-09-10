package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.*;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.IAppointmentService;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    @Autowired
    IAppointmentDAO appointmentDAO;
    @Autowired
    IPatientDAO patientDAO;
    @Autowired
    IDoctorDAO doctorDAO;
    @Autowired
    IAppSlotDAO appSlotDAO;
    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;
    @Resource
    SessionObject sessionObject;

    @Override
    public List<Appointment> getAllAppointmentByPatient(int userId) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppointmentByPatient(patient);
    }

    @Override
    public List<Appointment> getAllAppointmentByDoctor(Doctor doctor) {
        Doctor doctorFromDB = this.doctorDAO.getDoctorByDoctorId(doctor.getDoctorId());
        return this.appointmentDAO.getAppointmentByDoctor(doctorFromDB);
    }

    @Override
    public List<Appointment> getAllPatientAppointmentByDoctor(Doctor doctor, int patientId) {
        Patient patientFromDB = this.patientDAO.getPatientByPatientId(patientId);
        Doctor loggedDoctor = this.doctorDAO.getDoctorByUserId(this.sessionObject.getLoggedUser().getUserId());
        return this.appointmentDAO.getPatientAppointmentByDoctor(loggedDoctor, patientFromDB);
    }

    @Override
    public List<Appointment> getCurrentAppByPatient(int userId, Appointment.Status status) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppByStatus(patient, Appointment.Status.Zaplanowana);
    }

    @Override
    public List<Appointment> getCurrentAppByPatientAndDate(int userId, Appointment.Status status, Date keyword) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppByStatusAndDate(patient, Appointment.Status.Zaplanowana, keyword);
    }

    @Override
    public List<Appointment> getHistAppByPatient(int userId, Appointment.Status status) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppByStatus(patient, Appointment.Status.Zakończona);
    }

    @Override
    public boolean addAppointment(int appSlotId) {
        Patient patient = this.patientDAO.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId());
        AppSlot appSlot = this.appSlotDAO.getById(appSlotId);
        appSlot.setAvailable(false);
        Appointment newApp = new Appointment(0, patient, appSlot, Appointment.Status.Zaplanowana);
        this.appSlotDAO.update(appSlot);
        return this.appointmentDAO.save(newApp);
    }

    @Override
    public boolean delete(Appointment appointment) {
        Appointment appointmentFromDB = this.appointmentDAO.getById(appointment.getAppointmentId());
        return this.appointmentDAO.delete(appointmentFromDB);
    }

    @Override
    public boolean update(Appointment appointment) {
        Appointment appointmentFromDB = this.appointmentDAO.getById(appointment.getAppointmentId());
        appointmentFromDB.setPatient(appointmentFromDB.getPatient());
        appointmentFromDB.setAppSlot(appointmentFromDB.getAppSlot());
        appointmentFromDB.setStatus(appointmentFromDB.getStatus());
        return this.appointmentDAO.update(appointmentFromDB);
    }

    @Override
    public Appointment getById(int appointmentId) {
        return this.appointmentDAO.getById(appointmentId);
    }
}
