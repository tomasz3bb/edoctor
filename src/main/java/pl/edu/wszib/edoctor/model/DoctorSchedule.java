package pl.edu.wszib.edoctor.model;

import javax.persistence.*;
import java.sql.Time;
import java.time.DayOfWeek;

@Entity(name = "tdoctorschedule")
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorScheduleId;
    @OneToOne (fetch = FetchType.EAGER)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    private Day dayOfWeek;
    private Time startOfWork;
    private Time endOfWork;

    public DoctorSchedule() {
    }

    public DoctorSchedule(int doctorScheduleId, Doctor doctor, Day dayOfWeek, Time startOfWork, Time endOfWork) {
        this.doctorScheduleId = doctorScheduleId;
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.startOfWork = startOfWork;
        this.endOfWork = endOfWork;
    }

    public int getDoctorScheduleId() {
        return doctorScheduleId;
    }

    public void setDoctorScheduleId(int doctorScheduleId) {
        this.doctorScheduleId = doctorScheduleId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartOfWork() {
        return startOfWork;
    }

    public void setStartOfWork(Time startOfWork) {
        this.startOfWork = startOfWork;
    }

    public Time getEndOfWork() {
        return endOfWork;
    }

    public void setEndOfWork(Time endOfWork) {
        this.endOfWork = endOfWork;
    }
}
