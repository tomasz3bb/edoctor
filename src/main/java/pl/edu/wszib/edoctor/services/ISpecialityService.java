package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Speciality;

import java.util.List;

public interface ISpecialityService {
    List<Speciality> getAllSpecialities();
    Speciality getSpecialityById(int id);
    boolean addSpeciality(Speciality speciality);
    void deleteSpeciality(Speciality speciality);
    void updateSpeciality(Speciality speciality);
}
