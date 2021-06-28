package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IOfficeDAO;
import pl.edu.wszib.edoctor.model.Office;
import pl.edu.wszib.edoctor.services.IOfficeService;

import java.util.List;

@Service
public class OfficeServiceImpl implements IOfficeService {

    @Autowired
    IOfficeDAO officeDAO;

    @Override
    public List<Office> getAll() {
        return this.officeDAO.getAll();
    }

    @Override
    public Office getById(int officeId) {
        return this.officeDAO.getById(officeId);
    }

    @Override
    public boolean save(Office office) {
        return this.officeDAO.save(office);
    }

    @Override
    public boolean update(Office office) {
       return this.officeDAO.update(office);
    }

    @Override
    public boolean delete(Office office) {
       return this.officeDAO.delete(office);
    }
}
