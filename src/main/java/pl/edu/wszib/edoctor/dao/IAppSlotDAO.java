package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Doctor;

import java.sql.Date;
import java.util.List;

public interface IAppSlotDAO {
    boolean save(AppSlot appSlot);
    boolean update(AppSlot appSlot);
    boolean delete(AppSlot appSlot);
    AppSlot getById(int appSlotId);
    List<AppSlot> getAllByDoctorAndDate(Doctor doctor, Date keyword);
}