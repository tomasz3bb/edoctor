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
    public void save(Office office) {
        this.officeDAO.save(office);
    }

    @Override
    public void update(Office office) {
        this.officeDAO.update(office);
    }

    @Override
    public void delete(Office office) {
        this.officeDAO.delete(office);
    }
}
