package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.Office;

import java.util.List;

public interface IOfficeDAO {
    Office getById(int officeId);
    List<Office> getAll();
    void save(Office office);
    void delete(Office office);
    void update(Office office);
}
