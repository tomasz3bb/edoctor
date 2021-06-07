package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Speciality;

import java.util.List;

public interface ISpecialityService {
    List<Speciality> getAll();
    Speciality getSpecialityById(int id);
    boolean save(Speciality speciality);
    void delete(Speciality speciality);
    void update(Speciality speciality);
}
