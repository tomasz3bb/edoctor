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

    @Autowired
    ISpecialityDAO specialityDAO;


    @Override
    public List<Speciality> getAll() {
        return this.specialityDAO.getAll();
    }

    @Override
    public Speciality getSpecialityById(int id) {
        return this.specialityDAO.getSpecialityById(id);
    }

    @Override
    public boolean save(Speciality speciality) {
        Speciality newSpeciality = new Speciality(0, speciality.getSpecialityName());
        return this.specialityDAO.save(newSpeciality);
    }

    @Override
    public boolean delete(Speciality speciality) {
        Speciality specialityFromDB = this.specialityDAO.getSpecialityById(speciality.getSpecialityId());
        return this.specialityDAO.delete(specialityFromDB);
    }

    @Override
    public boolean update(Speciality speciality) {
        Speciality specialityFromDB = this.specialityDAO.getSpecialityById(speciality.getSpecialityId());
        specialityFromDB.setSpecialityName(speciality.getSpecialityName());
        return this.specialityDAO.update(specialityFromDB);
    }
}

