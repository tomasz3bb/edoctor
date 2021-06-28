package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IAppointmentDetailDAO;
import pl.edu.wszib.edoctor.model.AppointmentDetail;
import pl.edu.wszib.edoctor.services.IAppointmentDetailService;
import pl.edu.wszib.edoctor.session.SessionObject;

import javax.annotation.Resource;

@Service
public class AppointmentDetailServiceImpl implements IAppointmentDetailService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IAppointmentDetailDAO appointmentDetailDAO;

    @Override
    public AppointmentDetail getById(int id) {
        return this.appointmentDetailDAO.getById(id);
    }

    @Override
    public boolean add(AppointmentDetail appointmentDetail) {
        AppointmentDetail newAppDetail = new AppointmentDetail(0, appointmentDetail.getAppointment(), appointmentDetail.getDescription());
        return this.appointmentDetailDAO.save(newAppDetail);
    }

    @Override
    public boolean update(int appId) {
        AppointmentDetail appointmentDetail = this.appointmentDetailDAO.getById(appId);
        appointmentDetail.setAppointment(appointmentDetail.getAppointment());
        appointmentDetail.setDescription(appointmentDetail.getDescription());
        return this.appointmentDetailDAO.update(appointmentDetail);
    }

    @Override
    public boolean delete(int appId) {
        AppointmentDetail appointmentDetail = this.appointmentDetailDAO.getById(appId);
        return this.appointmentDetailDAO.delete(appointmentDetail);
    }
}
