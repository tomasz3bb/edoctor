package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IDoctorScheduleDAO;
import pl.edu.wszib.edoctor.dao.IOfficeDAO;
import pl.edu.wszib.edoctor.model.DoctorSchedule;
import pl.edu.wszib.edoctor.model.Office;
import pl.edu.wszib.edoctor.services.IDoctorScheduleService;
import pl.edu.wszib.edoctor.services.IDoctorService;
import pl.edu.wszib.edoctor.services.IOfficeService;

import java.util.List;

@Service
public class OfficeServiceImpl implements IOfficeService {

    @Autowired
    IOfficeDAO officeDAO;

    @Autowired
    IDoctorScheduleDAO doctorScheduleDAO;

    @Override
    public List<Office> getAll() {
        return this.officeDAO.getAll();
    }

    @Override
    public List<Office> getAllAvailable() {
        return this.officeDAO.getAllAvailable();
    }

    @Override
    public Office getById(int officeId) {
        return this.officeDAO.getById(officeId);
    }

    @Override
    public boolean save(Office office) {
        Office newOffice = new Office(0, office.getRoomNumber(), office.getFloor(), office.getDescription(), true);
        return this.officeDAO.save(newOffice);
    }

    @Override
    public boolean update(Office office) {
        Office officeFromDB = this.officeDAO.getById(office.getOfficeId());
        officeFromDB.setRoomNumber(office.getRoomNumber());
        officeFromDB.setFloor(office.getFloor());
        officeFromDB.setDescription(office.getDescription());
        officeFromDB.setAvailable(office.isAvailable());
        return this.officeDAO.update(officeFromDB);
    }

    @Override
    public boolean delete(Office office) {
       return this.officeDAO.delete(office);
    }

    @Override
    public boolean assignOffice(DoctorSchedule doctorSchedule) {
        DoctorSchedule scheduleFromDB = this.doctorScheduleDAO.getDoctorScheduleById(doctorSchedule.getDoctorScheduleId());
        scheduleFromDB.setOffice(doctorSchedule.getOffice());
        return this.doctorScheduleDAO.update(scheduleFromDB);
    }
}
