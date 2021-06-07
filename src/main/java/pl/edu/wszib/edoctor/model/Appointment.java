package pl.edu.wszib.edoctor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tappointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @OneToOne(fetch = FetchType.EAGER)
    private Patient patient;
    @OneToOne(fetch = FetchType.EAGER)
    private Doctor doctor;
    private Date appointmentDate;
    private String dayOfWeek;
    private LocalTime appointmentTimeStart;
    private LocalTime appointmentTimeEnd;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        Zaplanowana,
        Zakończona
    }

    public Appointment(int appointmentId, Patient patient, Doctor doctor, Date appointmentDate, LocalTime appointmentTimeStart, Status status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.dayOfWeek = appointmentDate.toLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl-PL"));
        this.appointmentTimeStart = appointmentTimeStart;
        this.appointmentTimeEnd = appointmentTimeStart.plusMinutes(30);
        this.status = status;
    }
}
