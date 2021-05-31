package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.services.IAppointmentService;

import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    @Autowired
    IPatientDAO patientDAO;

    @Override
    public List<Appointment> getAppointmentByPatient(int userId) {
        Patient patient = this.patientDAO.getPatientByUserId(userId);
        return this.patientDAO.getAppointmentByPatient(patient);
    }
}
