package pl.edu.wszib.edoctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
