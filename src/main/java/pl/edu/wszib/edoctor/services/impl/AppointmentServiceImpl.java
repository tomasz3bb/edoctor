package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IAppointmentDAO;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.services.IAppointmentService;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Date;
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
    public List<Appointment> getHistAppByPatient(int userId, Appointment.Status status) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppByStatus(patient, Appointment.Status.Zakończona);
    }

    @Override
    public boolean addAppointment(Appointment appointment, int doctorId) {
        Patient patient = this.patientDAO.getPatientByUserId(this.sessionObject.getLoggedUser().getUserId());
        Doctor doctor = this.doctorDAO.getDoctorByDoctorId(doctorId);
        List<DoctorSchedule> doctorScheduleList = this.doctorScheduleDAO.getAllByDoctor(doctor);
        Appointment newApp = new Appointment(0, patient, doctor, appointment.getAppointmentDate(),
                appointment.getAppointmentDate().toLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL")),
                appointment.getAppointmentTimeStart(), appointment.getAppointmentTimeStart().plusMinutes(30), Appointment.Status.Zaplanowana);
        List<Appointment> appointmentListByDoctor = this.appointmentDAO.getAppByDoctorAndDate(doctor, newApp.getAppointmentDate());
        for (Appointment app: appointmentListByDoctor) {
            for (DoctorSchedule schedule: doctorScheduleList) {
                Date today = new Date();
                if (newApp.getDayOfWeek().equals(schedule.getDayOfWeek())){
                    if (newApp.getAppointmentTimeStart().equals(app.getAppointmentTimeStart()) ||
                            newApp.getAppointmentTimeStart().isAfter(schedule.getStartOfWork()) ||
                            newApp.getAppointmentTimeStart().isBefore(schedule.getStartOfWork()) ||
                            newApp.getAppointmentTimeStart().isBefore(app.getAppointmentTimeEnd()) ||
                            newApp.getAppointmentTimeEnd().isBefore(app.getAppointmentTimeEnd()) ||
                            newApp.getAppointmentDate().before(today)
                    ){
                        return false;
                    }
                }
            }
        }
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
        appointmentFromDB.setAppointmentDate(appointmentFromDB.getAppointmentDate());
        appointmentFromDB.setAppointmentTimeStart(appointmentFromDB.getAppointmentTimeStart());
        appointmentFromDB.setAppointmentTimeEnd(appointmentFromDB.getAppointmentTimeEnd());
        appointmentFromDB.setPatient(appointmentFromDB.getPatient());
        appointmentFromDB.setDoctor(appointmentFromDB.getDoctor());
        appointmentFromDB.setDayOfWeek(appointmentFromDB.getDayOfWeek());
        appointmentFromDB.setStatus(appointmentFromDB.getStatus());
        return this.appointmentDAO.update(appointmentFromDB);
    }

    @Override
    public Appointment getById(int appId) {
        return this.appointmentDAO.getById(appId);
    }
}
