package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tappointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER)
    private DoctorSchedule doctorSchedule;
    private Date appointmentDate;
    private Time appointmentTimeStart;
    private Time appointmentTimeEnd;
    private String status;

}
