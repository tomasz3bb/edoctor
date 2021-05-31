package pl.edu.wszib.edoctor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String startOfWork;
    private String endOfWork;

}
