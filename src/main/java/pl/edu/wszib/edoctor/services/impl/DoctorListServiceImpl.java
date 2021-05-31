package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorDAO;
import pl.edu.wszib.edoctor.dao.IDoctorListDAO;
import pl.edu.wszib.edoctor.dao.IPatientDAO;
import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.DoctorList;
import pl.edu.wszib.edoctor.model.Patient;
import pl.edu.wszib.edoctor.services.IDoctorListService;

import java.util.List;

@Service
public class DoctorListServiceImpl implements IDoctorListService {

    @Autowired
    IDoctorDAO doctorDAO;

    @Autowired
    IPatientDAO patientDAO;

    @Autowired
    IDoctorListDAO doctorListDAO;

    @Override
    public List<DoctorList> getPatientsByDoctor(Doctor doctor) {
        Doctor doctorFromDB = this.doctorDAO.getDoctorByUserId(doctor.getUser().getUserId());
        return this.doctorListDAO.getPatientsByDoctor(doctorFromDB);
    }

    @Override
    public List<DoctorList> getDoctorsByPatient(Patient patient) {
        Patient patientFromDB = this.patientDAO.getPatientByUserId(patient.getUser().getUserId());
        return this.doctorListDAO.getDoctorsByPatient(patientFromDB);
    }
}
