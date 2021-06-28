package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Office;

import java.util.List;

public interface IOfficeService {
    List<Office> getAll();
    Office getById(int officeId);
    boolean save(Office office);
    boolean update(Office office);
    boolean delete(Office office);
}
