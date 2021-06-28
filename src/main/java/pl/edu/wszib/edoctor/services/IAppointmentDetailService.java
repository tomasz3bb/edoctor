package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.AppointmentDetail;

public interface IAppointmentDetailService {
    AppointmentDetail getById(int id);
    boolean add(AppointmentDetail appointmentDetail);
    boolean update(int appId);
    boolean delete(int appId);
}
