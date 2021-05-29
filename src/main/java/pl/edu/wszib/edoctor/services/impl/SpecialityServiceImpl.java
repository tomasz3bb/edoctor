package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.ISpecialityDAO;
import pl.edu.wszib.edoctor.model.Speciality;
import pl.edu.wszib.edoctor.services.ISpecialityService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecialityServiceImpl implements ISpecialityService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    ISpecialityDAO specialityDAO;


    @Override
    public List<Speciality> getAllSpecialities() {
        return this.specialityDAO.getAllSpecialities();
    }

    @Override
    public Speciality getSpecialityById(int id) {
        return this.specialityDAO.getSpecialityById(id);
    }

    @Override
    public boolean addSpeciality(Speciality speciality) {
        Speciality newSpeciality = new Speciality(0, speciality.getSpecialityName());
        return this.specialityDAO.addSpeciality(newSpeciality);
    }

    @Override
    public void deleteSpeciality(Speciality speciality) {
        Speciality specialityFromDB = this.specialityDAO.getSpecialityById(speciality.getSpecialityId());
        this.specialityDAO.deleteSpeciality(specialityFromDB);
    }

    @Override
    public void updateSpeciality(Speciality speciality) {
        Speciality specialityFromDB = this.specialityDAO.getSpecialityById(speciality.getSpecialityId());
        specialityFromDB.setSpecialityName(speciality.getSpecialityName());
        this.specialityDAO.updateSpeciality(specialityFromDB);
    }
}
