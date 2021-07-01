package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.model.Office;

import java.util.List;

public interface IOfficeService {
    List<Office> getAll();
    List<Office> getAllAvailable();
    Office getById(int officeId);
    boolean save(Office office);
    boolean update(Office office);
    boolean delete(Office office);
    boolean assignOffice(DoctorSchedule doctorSchedule);
}
