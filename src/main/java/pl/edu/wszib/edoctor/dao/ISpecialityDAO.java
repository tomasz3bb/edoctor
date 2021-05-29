package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Doctor;
import pl.edu.wszib.edoctor.model.Speciality;

import java.util.List;

public interface ISpecialityDAO {
    Speciality getSpecialityById(int id);
    List<Speciality> getAllSpecialities();
    void deleteSpeciality(Speciality speciality);
    void updateSpeciality(Speciality speciality);
    boolean addSpeciality(Speciality speciality);
}
