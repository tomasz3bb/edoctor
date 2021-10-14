package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.AppSlot;
import pl.edu.wszib.edoctor.model.Doctor;

import java.sql.Date;
import java.util.List;

public interface IAppSlotService {
    boolean addAppSlot(AppSlot appSlot, int doctorId);
    boolean update(AppSlot appSlot);
    boolean delete(AppSlot appSlot);
    AppSlot getById(int appointmentId);
    List<AppSlot> getAllByDoctorAndDate(Doctor doctor, Date keyword);
}