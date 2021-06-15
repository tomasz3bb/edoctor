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
import java.util.List;

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
        Appointment newApp = new Appointment(0, patient, doctor, appointment.getAppointmentDate(),
                appointment.getAppointmentTimeStart(), Appointment.Status.Zaplanowana);
        /*List<DoctorSchedule> doctorScheduleList = this.doctorScheduleDAO.getAllByDoctor(doctor);
        for (DoctorSchedule doctorSchedule : doctorScheduleList) {
            if (doctorSchedule.getDayOfWeek().getNameOfWeek().equals(newApp.getDayOfWeek())){
                if (doctorSchedule.getStartOfWork().equals(newApp.getAppointmentTimeStart().toString()));
            }
        }
        //TODO
         */
        return this.appointmentDAO.save(newApp);
    }

    @Override
    public boolean delete(int appId) {
        Appointment appointment = this.appointmentDAO.getById(appId);
        return this.appointmentDAO.delete(appointment);
    }

    @Override
    public boolean update(int appId) {
        Appointment appointment = this.appointmentDAO.getById(appId);
        return this.appointmentDAO.update(appointment);
    }

    @Override
    public Appointment getById(int appId) {
        return this.appointmentDAO.getById(appId);
    }

    @Override
    public boolean checkDate() {
        return false;
    }
}
