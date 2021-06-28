package pl.edu.wszib.edoctor.dao;

import pl.edu.wszib.edoctor.model.AppointmentDetail;

public interface IAppointmentDetailDAO {
    AppointmentDetail getById(int id);
    boolean save(AppointmentDetail appointmentDetail);
    boolean update(AppointmentDetail appointmentDetail);
    boolean delete(AppointmentDetail appointmentDetail);
}
