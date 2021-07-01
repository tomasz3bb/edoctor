package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Office;

import java.util.List;

public interface IOfficeDAO {
    Office getById(int officeId);
    List<Office> getAll();
    boolean save(Office office);
    boolean delete(Office office);
    boolean update(Office office);
    List<Office> getAllAvailable();
}
