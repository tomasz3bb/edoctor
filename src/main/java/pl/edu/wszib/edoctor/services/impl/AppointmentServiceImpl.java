package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IAppointmentDAO;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.services.IAppointmentService;
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
    @Resource
    SessionObject sessionObject;

    @Override
    public List<Appointment> getAppointmentByPatient(int userId) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.appointmentDAO.getAppointmentByPatient(patient);
    }

    @Override
    public List<Appointment> getAppointmentByDoctor(Doctor doctor, Patient patient) {
        Doctor loggedDoctor = this.doctorDAO.getDoctorByDoctorId(this.sessionObject.getLoggedUser().getUserId());
        Patient patientFromDB = this.patientDAO.getPatientByPatientId(patient.getPatientId());
        return this.appointmentDAO.getAppointmentByDoctor(loggedDoctor, patientFromDB);
    }
}
