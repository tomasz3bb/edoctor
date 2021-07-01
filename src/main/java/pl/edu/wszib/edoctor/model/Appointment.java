package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
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

}
