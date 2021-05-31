package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.*;
import pl.edu.wszib.edoctor.model.*;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.List;

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IDoctorDAO doctorDAO;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    ISpecialityDAO specialityDAO;

    @Autowired
    IDoctorListDAO doctorListDAO;

    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;

    @Override
    public List<Doctor> getAllDoctors() {
        return this.doctorDAO.getAllDoctors();
    }

    @Override
    public Doctor getDoctorByDoctorId(int doctorId) {
        return this.doctorDAO.getDoctorByDoctorId(doctorId);
    }

    @Override
    public Doctor getDoctorByUserId(int userId) {
        return this.doctorDAO.getDoctorByUserId(userId);
    }

    @Override
    public boolean addDoctor(Doctor doctor, User user) {
        User newUser = new User(0, user.getLogin(), user.getPassword(), User.Role.Lekarz);
        Doctor newDoctor = new Doctor(0, newUser, doctor.getName(), doctor.getSurname(),
                doctor.getPhone(), doctor.getSpeciality(), doctor.getPWZNumber());
        this.userDAO.addUser(newUser);
        return this.doctorDAO.addDoctor(newDoctor);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        Doctor doctorFromDB = this.doctorDAO.getDoctorByDoctorId(doctor.getDoctorId());
        User userFromDB = this.userDAO.getUserById(doctorFromDB.getUser().getUserId());
        this.userDAO.deleteUser(userFromDB);
        this.doctorDAO.deleteDoctor(doctorFromDB);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        Doctor doctorFromDB = this.doctorDAO.getDoctorByDoctorId(doctor.getDoctorId());
        doctorFromDB.setUser(doctor.getUser());
        doctorFromDB.setName(doctor.getName());
        doctorFromDB.setSurname(doctor.getSurname());
        doctorFromDB.setPhone(doctor.getPhone());
        doctorFromDB.setSpeciality(doctor.getSpeciality());

        this.doctorDAO.updateDoctor(doctorFromDB);
    }

}
