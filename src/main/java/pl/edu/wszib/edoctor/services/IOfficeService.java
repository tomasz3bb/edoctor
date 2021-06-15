package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Office;

import java.util.List;

public interface IOfficeService {
    List<Office> getAll();
    Office getById(int officeId);
    void save(Office office);
    void update(Office office);
    void delete(Office office);
}
