package pl.edu.wszib.edoctor.model;

import javax.persistence.*;
import java.sql.Time;

@Entity(name = "tappointmentslot")
public class AppointmentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentSlotId;
    @ManyToOne(fetch = FetchType.EAGER)
    private DoctorSchedule doctorSchedule;
    @ManyToOne(fetch = FetchType.EAGER)
    private Calendar calendar;
    private Time appointmentTimeStart;
    private Time appointmentTimeEnd;
    private String status;

    public AppointmentSlot() {
    }

    public AppointmentSlot(int appointmentSlotId, DoctorSchedule doctorSchedule, Calendar calendar, Time appointmentTimeStart, Time appointmentTimeEnd, String status) {
        this.appointmentSlotId = appointmentSlotId;
        this.doctorSchedule = doctorSchedule;
        this.calendar = calendar;
        this.appointmentTimeStart = appointmentTimeStart;
        this.appointmentTimeEnd = appointmentTimeEnd;
        this.status = status;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getAppointmentSlotId() {
        return appointmentSlotId;
    }

    public void setAppointmentSlotId(int appointmentSlotId) {
        this.appointmentSlotId = appointmentSlotId;
    }

    public DoctorSchedule getDoctorSchedule() {
        return doctorSchedule;
    }

    public void setDoctorSchedule(DoctorSchedule doctorSchedule) {
        this.doctorSchedule = doctorSchedule;
    }

    public Time getAppointmentTimeStart() {
        return appointmentTimeStart;
    }

    public void setAppointmentTimeStart(Time appointmentTimeStart) {
        this.appointmentTimeStart = appointmentTimeStart;
    }

    public Time getAppointmentTimeEnd() {
        return appointmentTimeEnd;
    }

    public void setAppointmentTimeEnd(Time appointmentTimeEnd) {
        this.appointmentTimeEnd = appointmentTimeEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
