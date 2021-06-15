package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Speciality;

import java.util.List;

public interface ISpecialityDAO {
    Speciality getSpecialityById(int id);
    List<Speciality> getAll();
    boolean delete(Speciality speciality);
    boolean update(Speciality speciality);
    boolean save(Speciality speciality);
}
