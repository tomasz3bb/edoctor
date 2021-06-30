package pl.edu.wszib.edoctor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.edoctor.dao.IAppointmentDAO;
import pl.edu.wszib.edoctor.dao.IAppointmentDetailDAO;
import pl.edu.wszib.edoctor.model.Appointment;
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

    @Autowired
    IAppointmentDAO appointmentDAO;

    @Override
    public AppointmentDetail getById(int id) {
        return this.appointmentDetailDAO.getById(id);
    }

    @Override
    public boolean add(Appointment appointment, AppointmentDetail appointmentDetail) {
        Appointment app = this.appointmentDAO.getById(appointment.getAppointmentId());
        app.setStatus(Appointment.Status.Zakończona);
        AppointmentDetail newAppDetail = new AppointmentDetail(0, app, appointmentDetail.getDescription());
        this.appointmentDAO.update(app);
        return this.appointmentDetailDAO.save(newAppDetail);
    }
}
