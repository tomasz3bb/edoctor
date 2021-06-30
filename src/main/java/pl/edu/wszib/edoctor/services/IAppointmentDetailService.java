package pl.edu.wszib.edoctor.services;

import pl.edu.wszib.edoctor.model.Appointment;
import pl.edu.wszib.edoctor.model.AppointmentDetail;

public interface IAppointmentDetailService {
    AppointmentDetail getById(int id);
    boolean add(Appointment appointment, AppointmentDetail appointmentDetail);
}
