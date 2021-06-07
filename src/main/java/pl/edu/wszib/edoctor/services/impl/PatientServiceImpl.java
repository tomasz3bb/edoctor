package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorListDAO;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.dao.IUserDAO;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.IPatientService;

import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    IPatientDAO patientDAO;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;

    @Autowired
    IDoctorListDAO doctorListDAO;

    @Override
    public List<Patient> getAll() {
        return this.patientDAO.getAll();
    }

    @Override
    public Patient getPatientByPatientId(int patientId) {
        return this.patientDAO.getPatientByPatientId(patientId);
    }

    @Override
    public Patient getPatientByUserId(int userId) {
        return this.patientDAO.getPatientByUserId(userId);
    }

    @Override
    public boolean save(Patient patient, User user) {
        User newUser = new User(0, user.getLogin(), user.getPassword(), User.Role.Pacjent);
        Patient newPatient = new Patient(0, newUser, patient.getName(), patient.getSurname(),
                patient.getPhone(), patient.getDateOfBirth(), patient.getPESEL());
        this.userDAO.save(newUser);
        return this.patientDAO.save(newPatient);
    }

    @Override
    public void delete(Patient patient) {
        Patient patientFromDB = this.patientDAO.getPatientByPatientId(patient.getPatientId());
        User userFromDB = this.userDAO.getUserById(patientFromDB.getUser().getUserId());
        this.userDAO.delete(userFromDB);
        this.patientDAO.delete(patientFromDB);
    }

    @Override
    public void update(Patient patient) {
        Patient patientFromDB = this.patientDAO.getPatientByPatientId(patient.getPatientId());
        patientFromDB.setName(patient.getName());
        patientFromDB.setSurname(patient.getSurname());
        patientFromDB.setPhone(patient.getPhone());
        patientFromDB.setDateOfBirth(patient.getDateOfBirth());
        patientFromDB.setPESEL(patient.getPESEL());

        this.patientDAO.update(patientFromDB);
    }
}
